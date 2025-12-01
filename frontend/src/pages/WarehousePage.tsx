import { useEffect, useState } from "react";
import axios from "axios";
import type { Warehouse } from "../types/Warehouse";
import WarehouseTable from "@/components/WarehouseTable";

export default function WarehousePage() {
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [newWH, setNewWH] = useState<Warehouse>({
        id: "",
        name: "",
        city: "",
        street: "",
        houseNumber: "",
        zipCode: "",
    });

    useEffect(() => {
        axios
            .get("/api/warehouse")
            .then((res) => setWarehouses(res.data))
            .catch(() => setError("Fehler beim Laden der Lagerhaus-Daten"))
            .finally(() => setLoading(false));
    }, []);

    const createWarehouse = () => {
        setSaving(true);
        axios
            .post("/api/warehouse", newWH)
            .then((res) => {
                setWarehouses((prev) => [...prev, res.data]);
                setNewWH({ id: "", name: "", city: "", street: "", houseNumber: "", zipCode: "" });
            })
            .finally(() => setSaving(false));
    };

    const deleteWarehouse = (id: string) => {
        axios.delete(`/api/warehouse/${id}`)
            .then(() => {
                setWarehouses((prev) => prev.filter((w) => w.id !== id));
            });
    };

    const updateWarehouse = (updated: Warehouse) => {
        setSaving(true);
        axios
            .put(`/api/warehouse/${updated.id}`, updated)
            .then(() =>
                setWarehouses((prev) =>
                    prev.map((w) => (w.id === updated.id ? updated : w))
                )
            )
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

            <h1 className="text-2xl font-bold mb-6 text-center">Lagerhäuser</h1>

            <div className="flex gap-2 justify-center mb-6">
                {["name", "city", "street", "houseNumber", "zipCode"].map((f) => (
                    <input
                        key={f}
                        placeholder={f}
                        className="border p-1 rounded"
                        value={newWH[f as keyof Warehouse] as string}
                        onChange={(e) => setNewWH({ ...newWH, [f]: e.target.value })}
                    />
                ))}
                <button
                    onClick={createWarehouse}
                    className="bg-green-600 text-white px-3 py-1 rounded"
                >
                    +
                </button>
            </div>

            <WarehouseTable
                data={warehouses}
                onDelete={deleteWarehouse}
                onUpdate={updateWarehouse}
            />
        </div>
    );
}
