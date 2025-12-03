import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";

import WarehousePage from "./pages/WarehousePage";
import ProductPage from "./pages/ProductPage";

export default function App() {
    return (
        <Routes>
            <Route element={<Layout />}>
                {/* Hauptseiten */}
                <Route path="/" element={<WarehousePage />} />
                {/*<Route path="/product" element={<ProductPage />} />*/}

                {/* Detailseiten */}
                {/*<Route path="/warehouse/:id" element={<WarehouseDetailPage />} />*/}
                {/*<Route path="/product/:id" element={<ProductDetailPage />} />*/}
                <Route path="/product/warehouse/:id" element={<ProductPage />} />
            </Route>
        </Routes>
    );
}

// import { Routes, Route } from "react-router-dom";
// import Layout from "./components/Layout";
// import ProtectedRoute from "./components/ProtectedRoute";
//
// import LoginPage from "./pages/LoginPage";
// import WarehousePage from "./pages/WarehousePage";
// import ProductPage from "./pages/ProductPage";
//
// export default function App() {
//     const user = null; // später durch AuthContext ersetzen
//
//     return (
//         <Routes>
//             {/* Login zuerst – immer öffentlich */}
//             <Route path="/login" element={<LoginPage />} />
//
//             {/* Geschützte Routen */}
//             <Route element={<ProtectedRoute user={user} />}>
//                 <Route element={<Layout />}>
//                     {/* Hauptseiten */}
//                     <Route path="/" element={<WarehousePage />} />
//                     {/*<Route path="/product" element={<ProductPage />} />*/}
//
//                     {/* Detailseiten */}
//                     <Route path="/product/warehouse/:id" element={<ProductPage />} />
//                 </Route>
//             </Route>
//         </Routes>
//     );
// }

