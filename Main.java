import java.text.NumberFormat; //format angka menjadi currency
import java.util.Locale; // lokasi dan bahasa untuk format uang
import java.util.Scanner; // membaca input dari user 

// ===== Representasi item menu =====
class Menu {
    String nama;
    int harga;
    String kategori; // "Makanan" / "Minuman"
    Menu(String nama, int harga, String kategori) {
        this.nama = nama; this.harga = harga; this.kategori = kategori;
    }
}

public class Main {

    // ===== 1) DATA MENU (ARRAY) =====
    static Menu[] menus = new Menu[] {
        // Makanan
        new Menu("Nasi Padang", 25000, "Makanan"),
        new Menu("Mie Goreng", 22000, "Makanan"),
        new Menu("Ayam Bakar", 30000, "Makanan"),
        new Menu("Sate Ayam",  28000, "Makanan"),
        // Minuman
        new Menu("Es Teh",      8000,  "Minuman"),
        new Menu("Kopi",        15000, "Minuman"),
        new Menu("Jus Mangga",  18000, "Minuman"),
        new Menu("Air Mineral", 6000,  "Minuman")
    };

    // ===== Util rupiah =====
    static String rp(int x) {
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
    return nf.format(x).replace(",00", "");
}

    // ===== 2) Tampilkan menu (tanpa loop) =====
    static void tampilkanMenu() {
        System.out.println("=== WARUNG NASI ABCD ===");
        System.out.println("=== DAFTAR MENU RESTORAN ===");
        System.out.println("[Makanan]");
        System.out.println("1. " + menus[0].nama + " - " + rp(menus[0].harga));
        System.out.println("2. " + menus[1].nama + " - " + rp(menus[1].harga));
        System.out.println("3. " + menus[2].nama + " - " + rp(menus[2].harga));
        System.out.println("4. " + menus[3].nama + " - " + rp(menus[3].harga));
        System.out.println("[Minuman]");
        System.out.println("5. " + menus[4].nama + " - " + rp(menus[4].harga));
        System.out.println("6. " + menus[5].nama + " - " + rp(menus[5].harga));
        System.out.println("7. " + menus[6].nama + " - " + rp(menus[6].harga));
        System.out.println("8. " + menus[7].nama + " - " + rp(menus[7].harga));
        System.out.println();
    }

    // ===== 3) Cari nama menu (if-else chain) =====
    static int cariIndex(String nama) {
        if (nama.equalsIgnoreCase(menus[0].nama)) return 0;
        else if (nama.equalsIgnoreCase(menus[1].nama)) return 1;
        else if (nama.equalsIgnoreCase(menus[2].nama)) return 2;
        else if (nama.equalsIgnoreCase(menus[3].nama)) return 3;
        else if (nama.equalsIgnoreCase(menus[4].nama)) return 4;
        else if (nama.equalsIgnoreCase(menus[5].nama)) return 5;
        else if (nama.equalsIgnoreCase(menus[6].nama)) return 6;
        else if (nama.equalsIgnoreCase(menus[7].nama)) return 7;
        return -1;
    }

    // ===== 4) Util perhitungan =====
    static int totalItem(int idx, int qty) {
        if (idx < 0 || qty <= 0) return 0;
        return menus[idx].harga * qty;
    }
    static boolean isMinuman(int idx){
        return idx >= 0 && "Minuman".equalsIgnoreCase(menus[idx].kategori);
    }

    // ===== 5) Promo (minuman yang sama; 1 gelas gratis; ambang dari total kotor) =====
    static int hitungPromo(int idx1,int q1,int idx2,int q2,int idx3,int q3,int idx4,int q4,int total){
        if (total <= 50000) return 0; // ambang dihitung dari total semua item
        int kandidat = 0; // harga 1 gelas minuman yang eligible (ambil tertinggi)
        if (isMinuman(idx1) && q1 >= 2 && menus[idx1].harga > kandidat) kandidat = menus[idx1].harga;
        if (isMinuman(idx2) && q2 >= 2 && menus[idx2].harga > kandidat) kandidat = menus[idx2].harga;
        if (isMinuman(idx3) && q3 >= 2 && menus[idx3].harga > kandidat) kandidat = menus[idx3].harga;
        if (isMinuman(idx4) && q4 >= 2 && menus[idx4].harga > kandidat) kandidat = menus[idx4].harga;
        return kandidat; // potongan 1 gelas
    }

    // ===== 6) Cetak struk =====
    static void cetakStruk(int idx1,int q1,int idx2,int q2,int idx3,int q3,int idx4,int q4){
        int t1 = totalItem(idx1,q1);
        int t2 = totalItem(idx2,q2);
        int t3 = totalItem(idx3,q3);
        int t4 = totalItem(idx4,q4);

        // TOTAL KOTOR (sebelum promo apapun)
        int total = t1 + t2 + t3 + t4;

        // PROMO A: Diskon 10% dari TOTAL KOTOR jika > 100k (sebelum BOGO)
        int diskon10 = (total > 100000) ? (int)(0.10 * total) : 0;

        // PROMO B: Beli 1 gratis 1 minuman (syarat berdasarkan TOTAL KOTOR)
        int potonganMinuman = hitungPromo(idx1,q1,idx2,q2,idx3,q3,idx4,q4,total);

        // Subtotal setelah kedua promo
        int subtotal = total - diskon10 - potonganMinuman;

        // Pajak 10% dari subtotal
        int pajak = (int)(0.10 * subtotal);

        // Service charge tetap
        int service = 20000;

        // Total akhir
        int grand = subtotal + pajak + service;

        // ===== TAMPILKAN STRUK =====
        System.out.println("\n========== WARUNG NASI ABCD ==========");
        System.out.println("=========== STRUK PEMBAYARAN =========");
        if (idx1>=0 && q1>0) System.out.println(menus[idx1].nama + " x" + q1 + " @" + rp(menus[idx1].harga) + " = " + rp(t1));
        if (idx2>=0 && q2>0) System.out.println(menus[idx2].nama + " x" + q2 + " @" + rp(menus[idx2].harga) + " = " + rp(t2));
        if (idx3>=0 && q3>0) System.out.println(menus[idx3].nama + " x" + q3 + " @" + rp(menus[idx3].harga) + " = " + rp(t3));
        if (idx4>=0 && q4>0) System.out.println(menus[idx4].nama + " x" + q4 + " @" + rp(menus[idx4].harga) + " = " + rp(t4));
        System.out.println("---------------------------------------");
        System.out.println("Total            : " + rp(total));
        if (diskon10>0)    System.out.println("Promo A - Diskon 10% (dari Total >100k): -" + rp(diskon10));
        if (potonganMinuman>0)System.out.println("Promo B - Beli 1 Gratis 1 (minuman sama):     -" + rp(potonganMinuman));
        System.out.println("Subtotal Setelah Promo : " + rp(subtotal));
        System.out.println("Pajak 10%              : " + rp(pajak));
        System.out.println("Biaya Pelayanan        : " + rp(service));
        System.out.println("GRAND TOTAL            : " + rp(grand));
        System.out.println("=======================================");
        System.out.println("Terima kasih telah berkunjung ke Warung Nasi ABCD!");
        System.out.println("=======================================\n");
    }

    // ===== Helper parsing input (ulang sampai format benar) =====
static int[] parseOrderRecursive(Scanner sc, String label) {
    System.out.print(label);

    String line;
    try {
        line = sc.nextLine();
    } catch (java.util.NoSuchElementException e) {
        return new int[]{-1, 0}; // EOF → skip
    }

    line = line.trim();

    if (line.isEmpty()) {
        return new int[]{-1, 0}; // ENTER kosong → skip
    }

    if (!line.contains("=")) {
        System.out.println("⚠️ Format salah. Gunakan: Nama Menu = jumlah (contoh: Nasi Padang = 2)");
        return parseOrderRecursive(sc, label); // panggil diri sendiri lagi
    }

    String[] a = line.split("=", 2);
    String nama = a[0].trim();
    String jumlahStr = a[1].trim();

    int qty;
    try {
        qty = Integer.parseInt(jumlahStr);
        if (qty <= 0) {
            System.out.println("⚠️ Jumlah harus lebih dari 0.");
            return parseOrderRecursive(sc, label);
        }
    } catch (NumberFormatException e) {
        System.out.println("⚠️ Jumlah bukan angka. Ulangi lagi.");
        return parseOrderRecursive(sc, label);
    }

    int idx = cariIndex(nama);
    if (idx == -1) {
        System.out.println("⚠️ Menu '" + nama + "' tidak ditemukan. Coba ketik sesuai daftar menu.");
        return parseOrderRecursive(sc, label);
    }

    return new int[]{idx, qty};
}

    // ===== 7) MAIN =====
    public static void main(String[] args) {
    tampilkanMenu();

    Scanner sc = new Scanner(System.in);
    System.out.println("Masukkan pesanan (maks 4).");
    System.out.println("Format: Nama Menu = jumlah  (contoh: Nasi Padang = 2)");
    System.out.println("Tekan ENTER kosong untuk melewati baris.\n");

    int[] o1 = parseOrderRecursive(sc, "Pesanan 1: ");
    int[] o2 = parseOrderRecursive(sc, "Pesanan 2: ");
    int[] o3 = parseOrderRecursive(sc, "Pesanan 3: ");
    int[] o4 = parseOrderRecursive(sc, "Pesanan 4: ");

    cetakStruk(o1[0], o1[1], o2[0], o2[1], o3[0], o3[1], o4[0], o4[1]);
    sc.close();
    }
}

