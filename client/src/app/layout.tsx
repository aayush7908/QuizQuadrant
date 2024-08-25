import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Navbar from "@/components/custom/navbar/Navbar";
import Authentication from "@/components/custom/Authentication";
import AuthState from "@/context/auth/AuthState";
import { Toaster } from "@/components/ui/toaster";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
	title: "QuizQuadrant"
};

export default async function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html lang="en">
			<body className={`${inter.className} h-screen`}>
				<AuthState>
					<Authentication />
					<Navbar />
					<div className="h-full pt-[4rem]">
						{children}
					</div>
					<Toaster />
				</AuthState>
			</body>
		</html>
	);
}
