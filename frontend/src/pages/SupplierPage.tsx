import { useEffect, useState } from "react"
import type { Supplier } from "../types/Supplier"
import SupplierTable from "@/components/SupplierTable";

export default function SupplierPage() {
    const [suppliers, setSuppliers] = useState<Supplier[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch("/api/supplier")
            .then(res => res.json())
            .then(data => {
                setSuppliers(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Fehler beim Laden der Lieferanten:", err);
                setLoading(false);
            });
    }, []);

    if (loading) return <p className="text-center mt-10">Lade Daten...</p>;

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-6 text-center">Lieferanten</h1>
            <SupplierTable data={suppliers} />
        </div>
    );
}