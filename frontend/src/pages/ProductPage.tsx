import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import EditableTable, { type Column } from "../components/EditableTable";
import { Plus } from "lucide-react";

//Product Schema
export type Product = {
    id: string;
    name: string;
    quantity: number;
    barcode: string;
    category: string; // Backend erwartet ENUM-Key
};

//Lagerhaus Schema
export type Warehouse = {
    id: string;
    name: string;
};

export default function ProductPage() {
    const { warehouseId } = useParams(); // ID aus URL holen

    // Produktliste im Zustand
    const [products, setProducts] = useState<Product[]>([]);

    // Aktuelles Lagerhaus
    const [warehouse, setWarehouse] = useState<Warehouse | null>(null);

    // Welches Produkt gerade editiert wird
    const [editingId, setEditingId] = useState<string | null>(null);

    // Ladezustand
    const [loading, setLoading] = useState(true);

    // Produkte laden
    useEffect(() => {
        axios.get(`/api/product/warehouse/${warehouseId}`).then(res => {
            setProducts(res.data);
            setLoading(false);
        });
    }, [warehouseId]);

    // Lagerhaus laden
    useEffect(() => {
        axios.get(`/api/warehouse/${warehouseId}`).then(res => {
            setWarehouse(res.data);
        });
    }, [warehouseId]);

    // Dropdown-Optionen für Kategorie
    const categoryOptions = [
        { value: "ELECTRONICS", label: "Elektronik" },
        { value: "SPORT_EQUIPMENT", label: "Sportartikel" },
        { value: "COSMETICS", label: "Kosmetik" },
        { value: "CLOTHING", label: "Kleidung" }
    ];

    // Tabellen-Spalten definieren
    const columns: Column<Product>[] = [
        { key: "name", label: "Produktname", editable: true },
        { key: "quantity", label: "Menge", editable: true, inputType: "number" },
        { key: "barcode", label: "Barcode", editable: true },

        {
            key: "category",
            label: "Kategorie",
            editable: true,
            inputType: "select",
            selectOptions: categoryOptions
        }
    ];

    // Speichern – POST bei neuen IDs, sonst PUT
    const handleSave = async (row: Product) => {
        if (row.id.startsWith("new-")) {
            const res = await axios.post("/api/product", {
                ...row,
                warehouseId // Bezug zum Lagerhaus
            });
            return res.data;
        }

        const res = await axios.put(`/api/product/${row.id}`, row);
        return res.data;
    };

    // Löschen im Backend
    const handleDelete = async (id: string) => {
        await axios.delete(`/api/product/${id}`);
    };

    // Neue leere Produktzeile einfügen + direkt Edit starten
    const handleAdd = () => {
        if (editingId) return;

        const id = "new-" + crypto.randomUUID();

        setProducts(prev => [
            {
                id,
                name: "",
                quantity: 0,
                barcode: "",
                category: "ELECTRONICS" // Default-Wert
            },
            ...prev
        ]);

        setEditingId(id);
    };

    if (loading) return <p className="p-4">Lade Produkte…</p>;

    return (
        <div className="max-w-5xl mx-auto mt-10">
            <div className="flex justify-between mb-6">
                <h1 className="text-xl font-bold">Lagerhaus: {warehouse?.name}</h1>

                {/* Neue Produktzeile */}
                <button
                    onClick={handleAdd}
                    disabled={!!editingId}
                    className={`flex items-center gap-2 px-4 py-2 rounded-xl
                        ${
                        editingId
                            ? "bg-gray-400 text-white cursor-not-allowed"
                            : "bg-blue-600 text-white hover:bg-blue-700"
                    }`}
                >
                    <Plus size={18} />
                    Produkt
                </button>
            </div>

            {/* Wiederverwendbare Tabelle */}
            <EditableTable
                columns={columns}
                data={products}
                setData={setProducts}
                onSave={handleSave}
                onDelete={handleDelete}
                editingId={editingId}
                setEditingId={setEditingId}
            />
        </div>
    );
}
