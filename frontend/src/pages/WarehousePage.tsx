import { useEffect, useState } from "react"
import type { Warehouse } from "../types/Warehouse"
import WarehouseTable from "@/components/WarehouseTable.tsx";

export default function WarehousePage() {
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch("/api/warehouse")
            .then(res => res.json())
            .then(data => {
                setWarehouses(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Fehler beim Laden der Lagerhäuser:", err);
                setLoading(false);
            });
    }, []);

    if (loading) return <p className="text-center mt-10">Lade Daten...</p>;

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-6 text-center">Lagerhäuser</h1>
            <WarehouseTable data={warehouses} />
        </div>
    );
}