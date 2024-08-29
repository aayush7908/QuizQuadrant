import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Navbar from "@/components/custom/navbar/Navbar";
import AuthState from "@/context/auth/AuthState";
import { Toaster } from "@/components/ui/toaster";
import SubjectState from "@/context/subject/SubjectState";
import App from "@/components/custom/App";
import RefreshState from "@/context/refresh/RefreshState";

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
					<SubjectState>
						<RefreshState>
							<Navbar />
							<App children={children} />
							<Toaster />
						</RefreshState>
					</SubjectState>
				</AuthState>
			</body>
		</html>
	);
}