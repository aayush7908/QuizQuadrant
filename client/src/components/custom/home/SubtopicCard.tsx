import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";

export function SubtopicCard() {
    return (
        <Card>
            <CardHeader className="border-b bg-muted">
                <CardTitle>
                    <Link href={"/practice"} className="hover:underline hover:text-blue-800">Subject</Link>
                </CardTitle>
            </CardHeader>
            <CardContent className="bg-muted/30">
                <ul className="pt-3 grid gap-2">
                    <li className="list-disc list-inside">
                        <Link href={"/practice"} className="hover:underline hover:text-blue-800">Subtopic</Link>
                    </li>
                    <li className="list-disc list-inside">
                        <Link href={"/practice"} className="hover:underline hover:text-blue-800">Subtopic</Link>
                    </li>
                    <li className="list-disc list-inside">
                        <Link href={"/practice"} className="hover:underline hover:text-blue-800">Subtopic</Link>
                    </li>
                </ul>
            </CardContent>
        </Card>
    );
}