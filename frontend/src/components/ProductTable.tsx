import { useReactTable, getCoreRowModel, getSortedRowModel, flexRender } from "@tanstack/react-table";
import type { ColumnDef, SortingState } from "@tanstack/react-table";
import { useState } from "react";
import { Pencil, Trash, Check, X } from "lucide-react";
import type { Product } from "@/types/Product";

type Props = {
    data: Product[];
    onDelete: (id: string) => void;
    onUpdate: (updated: Product) => void;
};

export default function ProductTable({ data, onDelete, onUpdate }: Props) {
    const [sorting, setSorting] = useState<SortingState>([]);
    const [editId, setEditId] = useState<string | null>(null); // ID des aktuell zu bearbeitenden Produkts
    const [editData, setEditData] = useState<Product | null>(null); // Temporäre Daten während der Bearbeitung

    const startEdit = (row: Product) => {
        setEditId(row.id); // Bearbeitungsmodus aktivieren
        setEditData({ ...row }); // Daten kopieren
    };

    const cancelEdit = () => {
        setEditId(null); // Bearbeitung abbrechen
        setEditData(null);
    };

    const saveEdit = () => {
        if (editData) onUpdate(editData); // Änderungen speichern
        setEditId(null);
        setEditData(null);
    };

    const columns: ColumnDef<Product>[] = [
        { accessorKey: "name", header: "Produktname" },
        { accessorKey: "barcode", header: "Barcode" },
        { accessorKey: "description", header: "Beschreibung" },
        { accessorKey: "quantity", header: "Menge" },

        // --- Edit Button ---
        {
            id: "edit",
            header: "",
            cell: ({ row }) =>
                editId === row.original.id ? (
                    <div className="flex gap-2" onClick={(e) => e.stopPropagation()}>
                        {/* Speichern */}
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                saveEdit();
                            }}
                            className="text-green-600 hover:text-green-800"
                        >
                            <Check size={18} />
                        </button>

                        {/* Abbrechen */}
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                cancelEdit();
                            }}
                            className="text-gray-600 hover:text-gray-800"
                        >
                            <X size={18} />
                        </button>
                    </div>
                ) : (
                    // Bearbeiten starten
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

        // --- Delete Button ---
        {
            id: "delete",
            header: "",
            cell: ({ row }) => (
                <button
                    onClick={(e) => {
                        e.stopPropagation();
                        if (confirm("Willst du dieses Produkt wirklich löschen?")) {
                            onDelete(row.original.id); // Produkt löschen
                        }
                    }}
                    className="text-red-600 hover:text-red-800"
                >
                    <Trash size={18} />
                </button>
            ),
        },
    ];

    const table = useReactTable({
        data,
        columns,
        state: { sorting }, // Sortierung im State
        onSortingChange: setSorting, // Sortierung aktualisieren
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
                                className="py-3 px-4 text-left font-semibold cursor-pointer select-none"
                                onClick={h.column.getToggleSortingHandler()} // Sortierung toggeln
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
                        className="border-t hover:bg-gray-50"
                    >
                        {row.getVisibleCells().map((cell) => {
                            const isEditing = editId === row.original.id;
                            const col = cell.column.id as keyof Product;

                            return (
                                <td key={cell.id} className="py-2 px-4">
                                    {isEditing && editData && col in editData ? (
                                        // Eingabefelder im Bearbeitungsmodus
                                        <input
                                            className="border p-1 rounded w-full"
                                            value={editData[col] as string | number}
                                            onClick={(e) => e.stopPropagation()}
                                            onChange={(e) =>
                                                setEditData({
                                                    ...editData,
                                                    [col]:
                                                        col === "quantity"
                                                            ? Number(e.target.value)
                                                            : e.target.value,
                                                })
                                            }
                                        />
                                    ) : (
                                        // Normalanzeige
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
