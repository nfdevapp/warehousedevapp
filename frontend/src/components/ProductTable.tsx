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

    // Zustand für Sortierung der Tabelle
    const [sorting, setSorting] = useState<SortingState>([]);

    // ID des Produkts, das aktuell bearbeitet wird
    const [editId, setEditId] = useState<string | null>(null);

    // Temporäre Daten während der Inline-Bearbeitung
    const [editData, setEditData] = useState<Product | null>(null);

    // Bearbeitungsmodus für ein Produkt aktivieren
    const startEdit = (row: Product) => {
        setEditId(row.id);
        setEditData({ ...row }); // Kopie der Daten zum Editieren
    };

    // Bearbeitung abbrechen (keine Änderungen übernehmen)
    const cancelEdit = () => {
        setEditId(null);
        setEditData(null);
    };

    // Änderungen übernehmen und Callback ausführen
    const saveEdit = () => {
        if (editData) onUpdate(editData);
        setEditId(null);
        setEditData(null);
    };

    // Definition der Spalten für TanStack Table
    const columns: ColumnDef<Product>[] = [
        { accessorKey: "name", header: "Produktname" },
        { accessorKey: "barcode", header: "Barcode" },
        { accessorKey: "description", header: "Beschreibung" },
        { accessorKey: "quantity", header: "Menge" },

        // ------------------- Edit Button -------------------
        {
            id: "edit",
            header: "",
            cell: ({ row }) =>
                // Prüfen ob diese Zeile gerade editiert wird
                editId === row.original.id ? (
                    <div className="flex gap-2" onClick={(e) => e.stopPropagation()}>
                        {/* Speichern */}
                        <button
                            onClick={(e) => {
                                e.stopPropagation(); // verhindert Zeilen-Klick
                                saveEdit();
                            }}
                            className="text-green-600 hover:text-green-800"
                        >
                            <Check size={18} />
                        </button>

                        {/* Abbrechen */}
                        <button
                            onClick={(e) => {
                                e.stopPropagation(); // verhindert Zeilen-Klick
                                cancelEdit();
                            }}
                            className="text-gray-600 hover:text-gray-800"
                        >
                            <X size={18} />
                        </button>
                    </div>
                ) : (
                    // Bearbeitung starten
                    <button
                        onClick={(e) => {
                            e.stopPropagation(); // verhindert Zeilen-Klick
                            startEdit(row.original);
                        }}
                        className="text-blue-600 hover:text-blue-800"
                    >
                        <Pencil size={18} />
                    </button>
                ),
        },

        // ------------------- Delete Button -------------------
        {
            id: "delete",
            header: "",
            cell: ({ row }) => (
                <button
                    onClick={(e) => {
                        e.stopPropagation(); // verhindert Zeilen-Klick
                        if (confirm("Willst du dieses Produkt wirklich löschen?")) {
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

    // Initialisierung der Tabelle mit TanStack
    const table = useReactTable({
        data,
        columns,
        state: { sorting },
        onSortingChange: setSorting, // Sortierung aktualisieren
        getCoreRowModel: getCoreRowModel(), // Basis (ungefilterte) Zeilen
        getSortedRowModel: getSortedRowModel(), // Sortierte Zeilen
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
                                onClick={h.column.getToggleSortingHandler()} // Klick => Sortierung toggelt
                            >
                                {flexRender(h.column.columnDef.header, h.getContext())}

                                {/* Sortier-Indikatoren */}
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
                                        // Eingabefeld sichtbar, wenn die Zeile im Bearbeitungsmodus ist
                                        <input
                                            className="border p-1 rounded w-full"
                                            value={editData[col] as string | number}
                                            onClick={(e) => e.stopPropagation()} // Kein Trigger der Zeile
                                            onChange={(e) =>
                                                setEditData({
                                                    ...editData,
                                                    [col]:
                                                        col === "quantity"
                                                            ? Number(e.target.value) // Menge = Zahl
                                                            : e.target.value,
                                                })
                                            }
                                        />
                                    ) : (
                                        // Normaler Zelleninhalt
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
