import { useReactTable, getCoreRowModel, getSortedRowModel, flexRender } from "@tanstack/react-table";
import type { ColumnDef, SortingState } from "@tanstack/react-table";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import type { Warehouse } from "@/types/Warehouse";

type Props = {
    data: Warehouse[];
};

export default function WarehouseTable({ data }: Props) {
    const navigate = useNavigate();
    const [sorting, setSorting] = useState<SortingState>([]);

    // ---- Spalten ----
    const columns: ColumnDef<Warehouse>[] = [
        {
            accessorKey: "id",
            header: "ID",
        },
        {
            accessorKey: "name",
            header: "Name",
        },
        {
            accessorKey: "city",
            header: "Stadt",
        },
        {
            accessorKey: "street",
            header: "Straße",
        },
        {
            accessorKey: "houseNumber",
            header: "Nr.",
        },
        {
            accessorKey: "zipCode",
            header: "PLZ",
        },
    ];

    // ---- Tabelle initialisieren ----
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
                {table.getHeaderGroups().map(headerGroup => (
                    <tr key={headerGroup.id}>
                        {headerGroup.headers.map(header => (
                            <th
                                key={header.id}
                                className="py-3 px-4 text-left font-semibold cursor-pointer select-none"
                                onClick={header.column.getToggleSortingHandler()}
                            >
                                {flexRender(header.column.columnDef.header, header.getContext())}
                                {header.column.getIsSorted() === "asc" && " ↑"}
                                {header.column.getIsSorted() === "desc" && " ↓"}
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
