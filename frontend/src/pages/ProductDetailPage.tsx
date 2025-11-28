export default function ProductDetailPage() {
    return (
        <div>
            <h1>ProductDetailPage</h1>
        </div>
    );
}
// import { useEffect, useState } from "react";
// import { useParams } from "react-router-dom";
// import axios from "axios";
//
// type Category = "ELECTRONICS" | "SPORT_EQUIPMENT" | "COSMETICS" | "CLOTHING";
//
// export type Product = {
//     id: string;
//     name: string;
//     barcode: string;
//     description: string;
//     quantity: number;
//     category: Category;
// };
//
// const categoryLabels: Record<Category, string> = {
//     ELECTRONICS: "Elektronik",
//     SPORT_EQUIPMENT: "Sportartikel",
//     COSMETICS: "Kosmetik",
//     CLOTHING: "Kleidung"
// };
//
// export default function ProductDetailPage() {
//     const { id } = useParams();
//     const [product, setProduct] = useState<Product | null>(null);
//     const [loading, setLoading] = useState(true);
//     const [message, setMessage] = useState("");
//
//     useEffect(() => {
//         if (!id) return;
//
//         axios
//             .get(`/api/product/${id}`)
//             .then((res) => {
//                 setProduct(res.data);
//                 setLoading(false);
//             })
//             .catch(() => {
//                 setMessage("Produkt konnte nicht geladen werden.");
//                 setLoading(false);
//             });
//     }, [id]);
//
//     function handleChange(
//         e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
//     ) {
//         if (!product) return;
//
//         const { name, value } = e.target;
//
//         setProduct({
//             ...product,
//             [name]: value
//         });
//     }
//
//     function handleSubmit(e: React.FormEvent) {
//         e.preventDefault();
//         if (!product) return;
//
//         axios
//             .put(`/api/product/${product.id}`, product)
//             .then(() => setMessage("Produkt erfolgreich aktualisiert!"))
//             .catch(() => setMessage("Fehler beim Aktualisieren des Produkts."));
//     }
//
//     if (loading) return <p>Lade Produkt…</p>;
//     if (!product) return <p>Produkt nicht gefunden.</p>;
//
//     return (
//         <div style={{ maxWidth: "500px", margin: "40px auto", padding: "20px" }}>
//             <h1 style={{ fontSize: "1.8rem", marginBottom: "20px", textAlign: "center" }}>
//                 Produkt bearbeiten
//             </h1>
//
//             {message && (
//                 <p style={{ textAlign: "center", marginBottom: "20px" }}>
//                     {message}
//                 </p>
//             )}
//
//             <form
//                 onSubmit={handleSubmit}
//                 style={{
//                     display: "flex",
//                     flexDirection: "column",
//                     gap: "16px"
//                 }}
//             >
//                 {/* Name */}
//                 <label style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
//                     Name:
//                     <input
//                         name="name"
//                         value={product.name}
//                         onChange={handleChange}
//                         className="input-field"
//                     />
//                 </label>
//
//                 {/* Barcode */}
//                 <label style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
//                     Barcode:
//                     <input
//                         name="barcode"
//                         value={product.barcode}
//                         onChange={handleChange}
//                         className="input-field"
//                     />
//                 </label>
//
//                 {/* Beschreibung */}
//                 <label style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
//                     Beschreibung:
//                     <textarea
//                         name="description"
//                         value={product.description}
//                         onChange={handleChange}
//                         className="input-field"
//                         rows={3}
//                     />
//                 </label>
//
//                 {/* Menge */}
//                 <label style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
//                     Menge:
//                     <input
//                         type="number"
//                         name="quantity"
//                         value={product.quantity}
//                         onChange={handleChange}
//                         className="input-field"
//                     />
//                 </label>
//
//                 {/* Kategorie */}
//                 <label style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
//                     Kategorie:
//                     <select
//                         name="category"
//                         value={product.category}
//                         onChange={handleChange}
//                         className="input-field"
//                     >
//                         {Object.entries(categoryLabels).map(([key, label]) => (
//                             <option key={key} value={key}>
//                                 {label}
//                             </option>
//                         ))}
//                     </select>
//                 </label>
//
//                 <button type="submit">Speichern</button>
//             </form>
//         </div>
//     );
// }
