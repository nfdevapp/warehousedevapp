// components/ProtectedRoute.jsx
import { useEffect, useState } from "react";
import { Navigate, Outlet } from "react-router-dom";
import axios from "axios";

export default function ProtectedRoute() {
    const [loading, setLoading] = useState(true);
    const [authenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        axios
            .get("/api/auth/me")
            .then(() => {
                setAuthenticated(true);
                setLoading(false);
            })
            .catch(() => {
                setAuthenticated(false);
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Lade...</div>;

    return authenticated ? <Outlet /> : <Navigate to="/login" replace />;
}
