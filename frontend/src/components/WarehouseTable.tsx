import { useReactTable, getCoreRowModel, getSortedRowModel, flexRender } from "@tanstack/react-table";
import type { ColumnDef, SortingState } from "@tanstack/react-table";
import { useState } from "react";
import { Pencil, Trash, Check, X } from "lucide-react";
import type { Warehouse } from "@/types/Warehouse";
import { useNavigate } from "react-router-dom";

// Komponententypen
// - data: Liste aller Lagerhäuser
// - onDelete: Funktion zum Löschen
// - onUpdate: Funktion zum Speichern von Änderungen

type Props = {
    data: Warehouse[];
    onDelete: (id: string) => void;
    onUpdate: (updated: Warehouse) => void;
};

export default function WarehouseTable({ data, onDelete, onUpdate }: Props) {
    const navigate = useNavigate();

    // Sortierstatus der Tabelle (ASC/DESC)
    const [sorting, setSorting] = useState<SortingState>([]);

    // Zustand für Editiermodus
    const [editId, setEditId] = useState<string | null>(null);
    const [editData, setEditData] = useState<Warehouse | null>(null);

    // Aktiviert Bearbeitungsmodus für eine Zeile
    const startEdit = (row: Warehouse) => {
        setEditId(row.id);
        setEditData({ ...row }); // Kopie für Bearbeitung
    };

    // Bricht Bearbeitung ab
    const cancelEdit = () => {
        setEditId(null);
        setEditData(null);
    };

    // Speichert Änderungen
    const saveEdit = () => {
        if (editData) onUpdate(editData);
        setEditId(null);
        setEditData(null);
    };

    // Tabellenspalten-Definitionen
    const columns: ColumnDef<Warehouse>[] = [
        { accessorKey: "name", header: "Name" },
        { accessorKey: "city", header: "Stadt" },
        { accessorKey: "street", header: "Straße" },
        { accessorKey: "houseNumber", header: "Nr." },
        { accessorKey: "zipCode", header: "PLZ" },

        // Bearbeiten-Buttons
        {
            id: "edit",
            header: "",
            cell: ({ row }) =>
                editId === row.original.id ? (
                    // Wenn Zeile im Editmodus ist
                    <div className="flex gap-2" onClick={(e) => e.stopPropagation()}>
                        <button
                            onClick={(e) => { e.stopPropagation(); saveEdit(); }}
                            className="text-green-600 hover:text-green-800"
                        >
                            <Check size={18} />
                        </button>
                        <button
                            onClick={(e) => { e.stopPropagation(); cancelEdit(); }}
                            className="text-gray-600 hover:text-gray-800"
                        >
                            <X size={18} />
                        </button>
                    </div>
                ) : (
                    // Wenn Zeile normal angezeigt wird
                    <button
                        onClick={(e) => {
                            e.stopPropagation();
                            startEdit(row.original);
                        }}
                        className="text-blue-600 hover:text-blue-800"
                    >
                        <Pencil size={18} />
                    </button>
                ),
        },

        // Löschen-Button
        {
            id: "delete",
            header: "",
            cell: ({ row }) => (
                <button
                    onClick={(e) => {
                        e.stopPropagation();
                        if (confirm("Willst du dieses Lagerhaus wirklich löschen?")) {
                            onDelete(row.original.id);
                        }
                    }}
                    className="text-red-600 hover:text-red-800"
                >
                    <Trash size={18} />
                </button>
            ),
        },
    ];

    // TanStack(Sammlung von Bibliotheken für Tabellen, Daten-Abfragen und Routing. Für deine dynamische Tabelle) Table erzeugen
    const table = useReactTable({
        data,
        columns,
        state: { sorting },
        onSortingChange: setSorting,
        getCoreRowModel: getCoreRowModel(),
        getSortedRowModel: getSortedRowModel(),
    });

    return (
        <div className="flex justify-center mt-8">
            <table className="min-w-[80%] border border-gray-300 rounded-lg shadow-md overflow-hidden">
                <thead className="bg-gray-100">
                {table.getHeaderGroups().map((hg) => (
                    <tr key={hg.id}>
                        {hg.headers.map((h) => (
                            <th
                                key={h.id}
                                className="py-3 px-4 text-left cursor-pointer select-none"
                                onClick={h.column.getToggleSortingHandler()} // Sortieren bei Klick
                            >
                                {flexRender(h.column.columnDef.header, h.getContext())}
                                {h.column.getIsSorted() === "asc" && " ↑"}
                                {h.column.getIsSorted() === "desc" && " ↓"}
                            </th>
                        ))}
                    </tr>
                ))}
                </thead>

                <tbody>
                {table.getRowModel().rows.map((row) => (
                    <tr
                        key={row.id}
                        className="border-t hover:bg-gray-50 cursor-pointer"
                        onClick={() => {
                            // Nur navigieren, wenn keine Zeile bearbeitet wird
                            if (!editId) navigate(`/product/warehouse/${row.original.id}`);
                        }}
                    >
                        {row.getVisibleCells().map((cell) => {
                            const isEditing = editId === row.original.id;
                            const col = cell.column.id as keyof Warehouse;

                            return (
                                <td key={cell.id} className="py-2 px-4">
                                    {/* Falls Editiermodus aktiv ist, Eingabefeld anzeigen */}
                                    {isEditing && editData && col in editData ? (
                                        <input
                                            className="border p-1 rounded w-full"
                                            value={editData[col] as string}
                                            onClick={(e) => e.stopPropagation()}
                                            onChange={(e) =>
                                                setEditData({
                                                    ...editData,
                                                    [col]: e.target.value,
                                                })
                                            }
                                        />
                                    ) : (
                                        // Standardanzeige der Zelle
                                        flexRender(cell.column.columnDef.cell, cell.getContext())
                                    )}
                                </td>
                            );
                        })}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
