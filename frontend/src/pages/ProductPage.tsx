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

    useEffect(() => {
        if (!id) return;

        async function loadData() {
            try {
                // Produkte laden
                const pRes = await axios.get(`/api/product/warehouse/${id}`);
                setProducts(pRes.data);

                // Lagerhaus laden
                const wRes = await axios.get(`/api/warehouse/${id}`);
                setWarehouse(wRes.data);

            } catch (err) {
                console.error("Fehler beim Laden:", err);
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, [id]);

    if (loading) return <p className="text-center mt-10">Lade Daten...</p>;

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-6 text-center">
                Lagerhaus: {warehouse?.name ?? "Unbekannt"}
            </h1>

            <ProductTable data={products} />
        </div>
    );
}
