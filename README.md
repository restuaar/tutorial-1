# Tutorial for Advance Programming Course 2023/2024

### SonarCloud

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=advance-programming-tutorial_tutorial-1&metric=coverage)](https://sonarcloud.io/summary/new_code?id=advance-programming-tutorial_tutorial-1)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=advance-programming-tutorial_tutorial-1&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=advance-programming-tutorial_tutorial-1)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=advance-programming-tutorial_tutorial-1&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=advance-programming-tutorial_tutorial-1)  
Link [SonarCloud](https://sonarcloud.io/summary/new_code?id=advance-programming-tutorial_tutorial-1)

### Web Deployment

Link [Web Deployment](https://eshop-restu-advance-programming-tutorial.koyeb.app/)

**Nama** : **Restu Ahmad Ar Ridho** <br/>
**NPM** : **2206028951** <br/>
**Kelas** : **Advance Programming - A**

# Tutorial 2

## Refleksi

### 1. List The Code Quality Issue(s)

1. **Field Injection is Not Recommended (Consistensy)**  
   Dependenci _framework_ injeksi seperti Spring mendukung injeksi ketergantungan dengan menggunakan anotasi seperti @Inject dan @Autowired. Anotasi ini dapat digunakan untuk menginjeksi beans melalui konstruktor, setter, dan injeksi lapangan.

   Secara umum, injeksi field tidak disarankan. Hal ini memungkinkan pembuatan objek dalam keadaan tidak valid dan membuat pengujian menjadi lebih sulit. Ketergantungan tidak eksplisit ketika menginstansiasi kelas yang menggunakan injeksi lapangan.

   **Strategi :** Mengubah objek yang menggunakan injeksi @Autowired dengan membuat inisiasi _class_ langsung.

2. **Immediately Return Expression (Intentionality)**  
   Mendeklarasikan variabel hanya untuk _return_ dianggap sebagai praktik yang buruk karena menambah kerumitan yang tidak perlu. Praktik ini dapat membuat kode menjadi lebih sulit untuk dibaca dan dipahami, karena langkah tambahan yang tidak menambah nilai apa pun. Daripada mendeklarasikan variabel dan kemudian langsung _return_, umumnya lebih baik _return_ secara langsung. Hal ini membuat kode menjadi lebih bersih, sederhana, dan lebih mudah dipahami.

   **Strategi :** Menghapus variabel yang tidak langsung me-_return_ langsung dan juga mengubahnya menjadi _return_ langsung.

3. **Modifier JUnit5 Unnecessary public (Intentionality)**  
   JUnit5 lebih toleran dalam hal visibilitas kelas dan metode pengujian daripada JUnit4, yang mengharuskan semuanya bersifat publik. Kelas dan metode uji dapat memiliki visibilitas apa pun kecuali privat. Namun disarankan untuk menggunakan visibilitas paket default untuk meningkatkan keterbacaan.

   **Strategi :** Mengubah class test case yang masih memiliki modifier masih `public` menjadi modifier default.

### 2. Current Implementation Has Met The Definition of CI/CD

Menurut saya dengan implementasi sekarang sudah memenuhi definisi dari CI/CD. Dengan github workflows ini, projek kita dapat melakukan automasi dalam CI yaitu pada _running test_ dan automasi juga terhadap CD pada _deployment_ yang otomatis akan terlaksanakan pada setiap kali kita melakukan `push` ke repository GitHub kita. Setiap kita melakukan `push` pada branch manapun kode kita akan melalui _test case_ dengan action yang tertera pada `ci.yml` dan melakukan scan terhadap kode kita dengan action dengan SonarCloud. Ketika dirasa sudah berhasil dan baik kode kita akan melakukan `merge` ke branch `main` dimana terdapat automasi untuk melakukan deployment ke PaaS Koyeb dan kode security menggunakan action `scorecard.yml`. Sehingga action-action tersebut membentuk workflows otomatis pada Software Development Lifecycle yang terdapat juga CI/CD.

<hr/>

<details>
<summary> Tutorial 1 </summary>

## Refleksi 1

### Clean Code dan Secure Coding

Setelah mengikuti tutorial dan exercise menggunakan Spring Boot terdapat beberapa dari clean code yang sudah di implementasikan seperti:

- **_Meaningful Name_**  
  Pada projek kali ini setiap variabel yang diberikan memiliki makna yang sesuai sehingga memudahkan sekali dalam mencari dan mengingat varibel yang akan digunakan sehingga saya bisa langsung tau apa isi dari variabel tersebut tanpa harus melihat kembali dimana variabel tersebut diinisiasi.

- **_Function_**  
  Fungsi yang dibuat tidak panjang dan juga setiap fungsi menangani tugas yang berbeda sehingga dalam melakukan _debugging_ mudah dalam menemukan kesalahan karena fungsi yang digunakan antar _`class`_ maupun berkas dalam kode kita seperti pada berkas `ProductRepository.java` setiap fungsi memiliki tugas yang berbeda untuk melakukan _create, edit, delete, findById_ maupun _findAll_ yang memiliki tugas sesuai dengan nama fungsi tersebut.

- **_Comment_**  
  Pada projek kali ini jarang menggunakan _comment_ dalam menulis kode, namun menurut saya ada beberapa hal yang perlu ditambahkan karena ada beberapa hal yang tidak sesuai seperti pada saat ini saya menggunakan method POST untuk melakukan _edit product_ dan GET untuk melakukan _delete product_ sehingga kedepannya bisa diliat kembali dan dilakukan penyesuaian yang dengan method yang sesuai. Selain itu juga saya menghindari penggunaan _comment_ seperti yang sudah dijelaskan bahwa **`Comment Do Not Make Up for Bad Code`** sehingga lebih menggunakan nama variabel atau fungsi yang lebih bermakna dan alur program sendiri yang menjelaskan juga.

- **_Objects and Data Structure_**  
  Object sudah dibuat secara private sehingga object tidak mudah dimanipulasi dan juga membuat suatu interface pada direktori `service` untuk mengurangi abstrak sehingga memudahkan untuk membuat kode.

- **_Error Handling_**  
  Masih ada beberapa yang perlu ditambah dalam menangani beberapa _error_ seperti pada saat user membuat _product_ ketika _name product_ yang kosong dan _quantity product_ yang kosong maupun nilai kurang dari 0.

## Refleksi 2

1. Setelah menulis unit test terdapat perasaan seperti kepuasan bisa membuat beberapa unit test apalagi ketika melihat test tersebut berhasil, dan juga lebih percaya diri terhadap kode yang kita buat. Menurut saya, _Code Coverage_ memiliki cakupan kode 100% tidak menjamin kode bebas dari _bug_ atau kesalahan. _Code Coverage_ merupakan suatu metrik untuk mengukur persentase kode yang kita buat dieksekusi.

2. Menggunakan kembali prosedur penyiapan dan variabel contoh dari rangkaian tes fungsional yang ada membuat konsistensi terjaga dan sudah terbiasa pada setiap test. Namun terdapat kemungkinan masalah pada _clean code_ seperti
   - Dengan semakin banyaknya test yang ditambahkan, terdapat risiko keberagaman pada prosedur setup dan variabel instance sehingga kemungkinan membutuhkan variabel dan _set up_ yang berbeda. Sehingga diperlukan pembaruan terhadap _set up_ baru.
   - Rangkaian tes yang baru dapat menyebabkan duplikasi kode jika variabel contoh sama dengan rangkaian tes yang sudah ada.
   - Beberapa nama kasus uji kurang deskriptif, sehingga menghambat pemahaman.
   </details>
