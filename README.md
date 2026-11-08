# Лаб 9: Код хамралт — JaCoCo

F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)

- Нэр: Э.Номинзул
- Оюутны код: B242270082

## Орчин

`java -version` гаралт:

```
openjdk version "17.0.13" 2024-10-15
OpenJDK Runtime Environment Temurin-17.0.13+11 (build 17.0.13+11)
OpenJDK 64-Bit Server VM Temurin-17.0.13+11 (build 17.0.13+11, mixed mode, sharing)
```

`mvn -version` гаралт:

```
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: C:\Users\Dell\OneDrive\MyJavaProject\apache-maven-3.9.9
Java version: 17.0.13, vendor: Eclipse Adoptium, runtime: C:\Program Files\Eclipse Adoptium\jdk-17.0.13.11-hotspot
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Командуудыг Windows 11 дээр Git Bash-аар ажиллуулсан.

## Хэсэг A: Happy path тест ба эхний хэмжилт

Хэвийн урсгалын 5 тест бичсэн (`letterGradeA`, `letterGradeB`, `gpaOfTwoGrades`, `scholarshipFull`, `scholarshipHalf`).
`results/jacoco-A.csv`-ээс: Line 16/25 = **64%**, Branch 20/41 ≈ **49%**
(`BRANCH_COVERED=20`, `BRANCH_MISSED=21`, `LINE_COVERED=16`, `LINE_MISSED=9`).

Ромботой (хагас хамрагдсан) мөрүүд нь `score >= 90`, `score >= 80`,
`gpa >= 3.8 && credits >= 15` зэрэг нөхцөлүүд: тест зөвхөн нэг талд (үнэн эсвэл худал) орсон.
Улаан (огт хүрээгүй) мөрүүд нь алдааны замууд: гурван функцийн `IllegalArgumentException`,
`hasViolation` үеийн `return 0`, мөн `"C"`, `"D"`, `"F"` буцаах мөрүүд.

Файлууд: `results/mvn-verify-A.txt`, `results/jacoco-A.csv`

## Хэсэг B: CFG ба Branch 100%

`scholarship()`-ийн CFG: [cfg.png](cfg.png). Нөхцөл бүрийг тусдаа зангилаа болгож
(JaCoCo нөхцөл бүрийг тусдаа салаа гэж тоолдог), ирмэг бүр дээр түүнийг дайрах тестийн нэрийг бичсэн.

Нийт **21 тест** шаардагдсан (`results/mvn-verify-B.txt`: `Tests run: 21`).
`results/jacoco-B.csv`-д `BRANCH_MISSED = 0`, `LINE_MISSED = 0` (Branch 41/41, Line 25/25).

Файлууд: `cfg.png`, `results/mvn-verify-B.txt`, `results/jacoco-B.csv`

## Хэсэг C: Давталтын заагийн тест (`gpa()`)

- 0 удаа: `gpaEmptyThrows` (хоосон массив), `gpaNullThrows` (`null`): хоёулаа `assertThrows`
- 1 удаа: `gpaSingleGrade` → `gpa([95]) == 4.0`
- 2+ удаа: `gpaManyGrades` → `gpa([95, 85, 75, 65]) == 2.5`

Эдгээр тест хамралтын тоог өсгөөгүй, учир нь Хэсэг B-ийн дараа хамралт аль хэдийн 100% байсан.
Гэхдээ хамралт нь мөр, салаа ажилласан эсэхийг л хэлдэг бол давталтын 0/1/2+ тест нь
давталтын тооноос хамаарсан алдаа (жишээ нь хоосон оролт, нэг элемент, нийлбэр/дундаж буруу) байхгүйг шалгадаг.
Тиймээс хамралт өсөхгүй ч тэдгээр нь шинэ төрлийн алдааг илрүүлдэг.

## Хэсэг D: Хамралт 100%, гэхдээ алдаа амьд

Спецификаци: 60–69.99 оноо "D". Зааг утгын 7 тест нэмсэн
(0, 59.99, 60, 69.99, 89.99, 90, 100).

`mvn test` үр дүн (`results/mvn-test-D-fail.txt`): `Tests run: 30, Failures: 1`, `BUILD FAILURE`.

- Унасан тест: `letterGradeScore60IsD`
- Алдааны мөр: `ScholarshipCalculatorTest.letterGradeScore60IsD:150 expected: <D> but was: <F>`

Шалтгаан: `letterGrade()` дотор `if (score > 60) return "D";` гэж бичсэн байсан тул яг 60 оноо "F" буцаадаг.
Спецификаци 60-ыг "D" гэдэг тул `>=` байх ёстой. Засвар:
`Fix letterGrade: > 60 -> >= 60, spec says 60 is D`.

Засвараас хойш бүх тест ногоон: `results/mvn-verify-final.txt` (`Tests run: 30, Failures: 0`),
`results/jacoco-final.csv`-д `BRANCH_MISSED = 0`, `LINE_MISSED = 0`.

### Гол дүгнэлт

Branch хамралт 100% байхад энэ алдаа үл үзэгдэгч байсан. Учир нь `score > 60` ба `score >= 60`
хоёулаа үнэн, худал гэсэн ижил хоёр салааг үүсгэдэг: 65 оноо үнэн салаагаар, 50 оноо худал салаагаар орсон тул
JaCoCo хоёр салаа хоёулаа хамрагдсан гэж тоолсон. Харин яг 60 гэсэн зааг утга тэдгээр хоёр шалгалтыг ялгадаг ба
зөвхөн тэр утгыг шалгасан тест л алдааг илрүүлнэ. Хамралт нь код ажилласан эсэх, ямар салаа дайрагдсаныг хэмждэг
боловч тэр салаа **зөв утгаар**, спецификацид нийцсэн эсэхийг хэмждэггүй. Тиймээс хамралт нь тест хангалттай
эсэхийг үзүүлэх зайлшгүй хэрэгсэл боловч хангалттай нотолгоо биш; зааг утгын шинжилгээ шаардлагатай.

