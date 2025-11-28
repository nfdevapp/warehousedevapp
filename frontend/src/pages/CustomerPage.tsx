import { useEffect, useState } from "react"
import type { Customer } from "../types/Customer"
import CustomerTable from "@/components/CustomerTable";

export default function CustomerPage() {
    const [customers, setCustomers] = useState<Customer[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch("/api/customer")
            .then(res => res.json())
            .then(data => {
                setCustomers(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Fehler beim Laden der Kunden:", err);
                setLoading(false);
            });
    }, []);

    if (loading) return <p className="text-center mt-10">Lade Daten...</p>;

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-6 text-center">Kunden</h1>
            <CustomerTable data={customers} />
        </div>
    );
}