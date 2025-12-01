import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import type { Product } from "../types/Product";
import type { Warehouse } from "../types/Warehouse";
import ProductTable from "@/components/ProductTable";

export default function ProductPage() {
    const { id } = useParams();

    const [products, setProducts] = useState<Product[]>([]);
    const [warehouse, setWarehouse] = useState<Warehouse | null>(null);

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [newProduct, setNewProduct] = useState<Product>({
        id: "",
        name: "",
        barcode: "",
        description: "",
        quantity: 0,
        warehouseId: id ?? "",
    });

    useEffect(() => {
        if (!id) return;

        async function loadData() {
            try {
                const [pRes, wRes] = await Promise.all([
                    axios.get(`/api/product/warehouse/${id}`),
                    axios.get(`/api/warehouse/${id}`),
                ]);

                setProducts(pRes.data);
                setWarehouse(wRes.data);

            } catch (err) {
                console.error(err);
                setError("Fehler beim Laden der Produktdaten.");
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, [id]);

    // Produkt anlegen
    const createProduct = () => {
        if (!id) return;
        setSaving(true);

        axios.post("/api/product", { ...newProduct, warehouseId: id })
            .then((res) => {
                setProducts((prev) => [...prev, res.data]);
                setNewProduct({
                    id: "",
                    name: "",
                    barcode: "",
                    description: "",
                    quantity: 0,
                    warehouseId: id,
                });
            })
            .finally(() => setSaving(false));
    };

    // Produkt löschen
    const deleteProduct = (pid: string) => {
        axios.delete(`/api/product/${pid}`)
            .then(() => {
                setProducts((prev) => prev.filter((p) => p.id !== pid));
            });
    };

    // Produkt bearbeiten
    const updateProduct = (updated: Product) => {
        setSaving(true);

        axios.put(`/api/product/${updated.id}`, updated)
            .then(() => {
                setProducts((prev) =>
                    prev.map((p) => (p.id === updated.id ? updated : p))
                );
            })
            .finally(() => setSaving(false));
    };

    if (loading) return <p className="text-center mt-10">Lade Daten...</p>;
    if (error) return <p className="text-center mt-10 text-red-600">{error}</p>;

    return (
        <div className="p-6">
            {saving && (
                <p className="text-center mb-4 text-green-600 font-semibold">
                    Speichere Daten...
                </p>
            )}

            <h1 className="text-2xl font-bold mb-6 text-center">
                Lagerhaus: {warehouse?.name ?? "Unbekannt"}
            </h1>

            {/* Eingabefelder für neues Produkt */}
            <div className="flex gap-2 justify-center mb-6">
                {["name", "barcode", "description"].map((field) => (
                    <input
                        key={field}
                        placeholder={field}
                        className="border p-1 rounded"
                        value={newProduct[field as keyof Product] as string}
                        onChange={(e) =>
                            setNewProduct({ ...newProduct, [field]: e.target.value })
                        }
                    />
                ))}

                <input
                    type="number"
                    placeholder="quantity"
                    className="border p-1 rounded"
                    value={newProduct.quantity}
                    onChange={(e) =>
                        setNewProduct({ ...newProduct, quantity: Number(e.target.value) })
                    }
                />

                <button
                    onClick={createProduct}
                    className="bg-green-600 text-white px-3 py-1 rounded"
                >
                    +
                </button>
            </div>

            <ProductTable
                data={products}
                onDelete={deleteProduct}
                onUpdate={updateProduct}
            />
        </div>
    );
}
