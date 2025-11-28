import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <nav className="w-full bg-white border-b shadow-sm sticky top-0 z-50">
            <div className="max-w-6xl mx-auto px-4">
                <ul className="flex flex-row justify-center items-center gap-10 py-4 text-lg font-medium">
                    <li>
                        <Link to="/" className="hover:text-blue-600">Lagerhäuser</Link>
                    </li>
                    <li>
                        <Link to="/product" className="hover:text-blue-600">Produkte</Link>
                    </li>
                    <li>
                        <Link to="/supplier" className="hover:text-blue-600">Lieferanten</Link>
                    </li>
                    <li>
                        <Link to="/customer" className="hover:text-blue-600">Kunden</Link>
                    </li>
                </ul>
            </div>
        </nav>
    );
}
