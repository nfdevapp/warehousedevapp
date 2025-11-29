import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";

import WarehousePage from "./pages/WarehousePage";
import WarehouseDetailPage from "./pages/WarehouseDetailPage";
import ProductPage from "./pages/ProductPage";
import ProductDetailPage from "./pages/ProductDetailPage";

export default function App() {
    return (
        <Routes>
            <Route element={<Layout />}>
                {/* Hauptseiten */}
                <Route path="/" element={<WarehousePage />} />
                <Route path="/product" element={<ProductPage />} />

                {/* Detailseiten */}
                <Route path="/warehouse/:id" element={<WarehouseDetailPage />} />
                <Route path="/product/:id" element={<ProductDetailPage />} />
                <Route path="/product/warehouse/:id" element={<ProductPage />} />
            </Route>
        </Routes>
    );
}
