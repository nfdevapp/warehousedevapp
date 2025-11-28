import Navbar from "./Navbar.tsx";
import * as React from "react";
import Footer from "../Footer.tsx";
import Title from "../Title.tsx";

type LayoutProps = {
    children: React.ReactNode;
}

export default function Layout({children}: LayoutProps) {
    return (
        <>
            <header>
                <Title />
                <Navbar />
            </header>
            <main>
                {children}
            </main>
            <footer><Footer /></footer>
        </>
    );
}