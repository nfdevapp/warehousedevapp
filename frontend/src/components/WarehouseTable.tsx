import { useReactTable, getCoreRowModel, getSortedRowModel, flexRender } from "@tanstack/react-table";
import type { ColumnDef, SortingState } from "@tanstack/react-table";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Pencil, Trash } from "lucide-react";
import type { Warehouse } from "@/types/Warehouse";

type Props = {
    data: Warehouse[];
    onDelete: (id: string) => void;
};

export default function WarehouseTable({ data, onDelete }: Props) {
    const navigate = useNavigate();
    const [sorting, setSorting] = useState<SortingState>([]);

    const columns: ColumnDef<Warehouse>[] = [
        { accessorKey: "id", header: "ID" },
        { accessorKey: "name", header: "Name" },
        { accessorKey: "city", header: "Stadt" },
        { accessorKey: "street", header: "Straße" },
        { accessorKey: "houseNumber", header: "Nr." },
        { accessorKey: "zipCode", header: "PLZ" },

        {
            id: "edit",
            header: "",
            cell: ({ row }) => (
                <button
                    onClick={e => { e.stopPropagation(); navigate(`/warehouse/edit/${row.original.id}`); }}
                    className="text-blue-600 hover:text-blue-800"
                >
                    <Pencil size={18} />
                </button>
            ),
        },

        {
            id: "delete",
            header: "",
            cell: ({ row }) => (
                <button
                    onClick={e => {
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
        }
    ];

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
                {table.getHeaderGroups().map(hg => (
                    <tr key={hg.id}>
                        {hg.headers.map(h => (
                            <th
                                key={h.id}
                                className="py-3 px-4 text-left cursor-pointer select-none"
                                onClick={h.column.getToggleSortingHandler()}
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
                {table.getRowModel().rows.map(row => (
                    <tr
                        key={row.id}
                        className="border-t hover:bg-gray-50 cursor-pointer"
                        onClick={() => navigate(`/product/warehouse/${row.original.id}`)}
                    >
                        {row.getVisibleCells().map(cell => (
                            <td key={cell.id} className="py-2 px-4">
                                {flexRender(cell.column.columnDef.cell, cell.getContext())}
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
