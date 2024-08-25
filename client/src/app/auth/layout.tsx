export default function AuthLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<div className="min-h-full w-full flex justify-center items-center">
			{children}
		</div>
	);
}
