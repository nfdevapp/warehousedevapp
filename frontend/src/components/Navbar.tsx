import { Link, useLocation } from "react-router-dom";
// import { useAuth } from "../context/AuthContext";

export default function Navbar() {
    const { pathname } = useLocation();
    // const { logout } = useAuth();
    const active = (p: string) => pathname === p || (p !== "/" && pathname.startsWith(p));

    return (
        <nav className="w-full bg-white border-b shadow-sm sticky top-0 z-50">
            <ul className="flex justify-center gap-10 py-4 text-lg">
                <li>
                    <Link to="/" className={active("/") ? "text-blue-600" : "hover:text-blue-600"}>
                        Lagerhäuser
                    </Link>
                </li>
                <li>
                    <Link to="/product" className={active("/product") ? "text-blue-600" : "hover:text-blue-600"}>
                        Produkte
                    </Link>
                </li>

                {/* Logout */}
                <li>
                    <button
                        // onClick={logout}
                        className="hover:text-red-600 text-red-500"
                    >
                        Logout
                    </button>
                </li>
            </ul>
        </nav>
    );
}
