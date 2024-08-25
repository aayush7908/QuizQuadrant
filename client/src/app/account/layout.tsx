import { SideMenu } from "@/components/custom/account/SideMenu";

export default function AccountLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <div className="h-full w-full flex">
            <SideMenu />
            <div className="h-full w-full md:w-[calc(100%-17rem)] pb-[3rem] md:ms-[17rem] md:pb-0">
                {children}
            </div>
        </div>
    );
}
