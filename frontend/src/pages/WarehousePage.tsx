import { useEffect, useState } from "react";
import type { Warehouse } from "../types/Warehouse";
import WarehouseTable from "@/components/WarehouseTable";

export default function WarehousePage() {
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);

    const [newWH, setNewWH] = useState<Warehouse>({
        id: "", name: "", city: "", street: "", houseNumber: "", zipCode: ""
    });

    useEffect(() => {
        fetch("/api/warehouse")
            .then(res => res.json())
            .then(data => { setWarehouses(data); setLoading(false); });
    }, []);

    const createWarehouse = () => {
        setSaving(true);

        fetch("/api/warehouse", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newWH)
        })
            .then(res => res.json())
            .then(saved => {
                setWarehouses(prev => [...prev, saved]);
                setNewWH({ id: "", name: "", city: "", street: "", houseNumber: "", zipCode: "" });
                setSaving(false);
            });
    };

    const deleteWarehouse = (id: string) => {
        fetch(`/api/warehouse/${id}`, { method: "DELETE" })
            .then(() => setWarehouses(prev => prev.filter(w => w.id !== id)));
    };

    if (loading) return <p className="text-center mt-10 text-red-600">Lade Daten...</p>;

    return (
        <div className="p-6">

            {saving && (
                <p className="text-center mb-4 text-green-600 font-semibold">
                    Speichere Daten...
                </p>
            )}

            <h1 className="text-2xl font-bold mb-6 text-center">Lagerhäuser</h1>

            <div className="flex gap-2 justify-center mb-6">
                {["name","city","street","houseNumber","zipCode"].map(f => (
                    <input key={f} placeholder={f}
                           className="border p-1 rounded"
                           value={newWH[f as keyof Warehouse] as string}
                           onChange={(e)=>setNewWH({ ...newWH, [f]: e.target.value })}/>
                ))}
                <button onClick={createWarehouse}
                        className="bg-green-600 text-white px-3 py-1 rounded">
                    +
                </button>
            </div>

            <WarehouseTable data={warehouses} onDelete={deleteWarehouse} />
        </div>
    );
}
