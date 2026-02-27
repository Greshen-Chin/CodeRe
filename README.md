#   Dokumentasi Refactoring Game RPG
        Deskripsi Proyek
        Proyek ini merupakan hasil refactoring dari kode prosedural Game.java menjadi struktur Codingan yang lebih baik lagi dengan menerapkan konsep OOP, SOLID, Dan teori Refactoring. Tujuannya adalah untuk menurunkan Technical Debt dan meningkatkan kualitas desain perangkat lunak agar lebih mudah dipahami dan dimodifikasi.

===============================================================================================================================

        Kapan kita harus melakukan Refactoring dan kapan kita tidak perlu Refactoring ?

        kita perlu melakukan Refactoring disaat kita ingin:
        - Menambah Fitur Baru: Waktu terbaik untuk refactor adalah tepat sebelum menambahkan fitur. Jika struktur kode saat ini mempersulit penambahan fitur, perbaiki strukturnya terlebih dahulu agar pekerjaan selanjutnya lebih mudah.
        - Memperbaiki Bug: Saat memperbaiki bug, refactoring membantu memperjelas struktur program dan asumsi yang Anda buat, sehingga bug lebih mudah ditemukan dan tidak muncul lagi di tempat yang sama.
        - Melakukan Code Review: Refactoring selama peninjauan kode membantu menyebarkan pengetahuan dalam tim, mempermudah pemahaman sistem yang besar, dan menghasilkan kode yang lebih jelas bagi pengembang lain.
        - Melihat "Bad Smell": Ketika Anda menemukan struktur kode yang bermasalah (seperti metode yang terlalu panjang atau kelas yang terlalu besar), itulah indikasi kuat bahwa refactoring diperlukan.

        kita tidak perlu melakukan Refactoring disaat kita:
        - Kode Berantakan tetapi Tidak Perlu Dimodifikasi: Jika Anda menemukan kode yang buruk namun kode tersebut tidak perlu diubah atau dipahami cara kerjanya untuk tugas saat ini, Anda tidak perlu melakukan refactoring.
        - Lebih Mudah Menulis Ulang (Rewrite): Jika kode sudah terlalu hancur sehingga menulis ulang dari nol jauh lebih cepat dan mudah daripada memperbaikinya, maka refactoring sebaiknya dihindari.
        - Dekat dengan deadline : Meskipun refactoring mempercepat pengembangan dalam jangka panjang, dalam situasi darurat di mana fokus utama adalah fungsionalitas segera tanpa rencana perubahan di masa depan, refactoring bisa ditunda.
        
===============================================================================================================================

        Analisis Bad Code Smell, Berdasarkan materi yang dipelajari di kelas code lama atau di dalam folder Game sudah melanggar code smell sebagai berikut:

        The Bloaters
        - Large Class: Kelas Game menangani Gacha, Battle, dan data Player sekaligus.
        - Long Method: Metode fight() memiliki logika yang terlalu panjang dan kompleks.
        - Primitive Obsession: Data statistik karakter hanya menggunakan tipe data primitif int/String.

        Object Orientation Abuser
        - Switch Statement: Penggunaan if-else untuk membedakan aksi karakter berdasarkan nama string.
        - Temporary Field: Variabel status musuh (stun/burn) yang hanya digunakan saat battle diletakkan sebagai variabel global kelas.

        Change Preventer
        - Divergent Change: Satu kelas harus diubah untuk alasan yang berbeda-beda (fitur baru, bug, atau balancing).
        - Shotgun Surgery: Menambah satu karakter baru mengharuskan perubahan di banyak metode berbeda.

        The Dispensable
        - Duplicate Code: Logika perhitungan batas HP dan damage yang berulang di berbagai tempat.

        The Couplers
        - Inappropriate Intimacy: Semua metode mengakses dan memodifikasi data statis secara langsung tanpa enkapsulasi.

===============================================================================================================================
       
        Perbaikan yang Dilakukan:

        1. Encapsulation: Data pemain dipindahkan ke kelas Player.java dengan akses melalui getter/setter.

        2. Polymorphism: Membuat kelas abstrak Character.java dan subclass spesifik (Saber, Grock, dll) untuk menggantikan logika if-else pertempuran.

        3. Separation of Concerns: Memisahkan logika menu (MenuSystem.java), logika pertempuran (Battle.java), dan layanan gacha (GachaService.java).

        4. Status Effect Pattern: Menggunakan interface StatusEffect.java untuk menangani efek Burn dan Stun secara modular.

        5. Utility Class: Menggunakan ClampUtil.java untuk standarisasi perhitungan batas nilai variabel.

===============================================================================================================================

        Manfaat Refactoring

        1. Maintainability: Kode lebih mudah dipelihara karena setiap kelas memiliki tanggung jawab tunggal.

        2. Readability: Alur logika pertempuran lebih bersih dan mudah dipahami oleh pengembang lain.

        3. Extensibility: Penambahan karakter atau fitur baru dapat dilakukan tanpa risiko merusak logika utama (Shotgun Surgery teratasi). JavaProject berisi game merupakan code yang belum di perbaiki alias belum mengalaqmi fase code reeinggenering 