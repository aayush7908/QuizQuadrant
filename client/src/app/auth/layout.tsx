"use client"

import { AuthContext } from "@/context/auth/AuthContext";
import { usePathname, useRouter } from "next/navigation";
import { useContext, useEffect } from "react";

export default function AuthLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {

	const { user } = useContext(AuthContext);
	const router = useRouter();
	const path = usePathname();

	useEffect(() => {
		if (path !== "/auth/logout" && user) {
			router.push("/");
		}
	}, []);

	return (
		<div className="min-h-full w-full flex justify-center items-center">
			{children}
		</div>
	);
}
