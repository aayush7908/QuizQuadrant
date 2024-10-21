import {
    Card,
    CardContent,
    CardHeader,
    CardTitle
} from "@/components/ui/card";
import Link from "next/link";
import Subject from "@/app/_types/subject";

export function SubtopicCard({
    subject
}: {
    subject: Subject
}) {
    return (
        <Card className="bg-muted/30">
            <CardHeader className="border-b bg-muted">
                <CardTitle>
                    <Link
                        href={`/practice/subject/${subject.id}`}
                        className="hover:underline hover:text-blue-800"
                    >
                        {subject.name}
                    </Link>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <ul className="pt-3 grid gap-2">
                    {
                        subject.subtopics.map((subtopic, index) => {
                            return (
                                <li key={index} className="list-disc list-inside">
                                    <Link
                                        href={`/practice/subtopic/${subtopic.id}`}
                                        className="hover:underline hover:text-blue-800"
                                    >
                                        {subtopic.name}
                                    </Link>
                                </li>
                            )
                        })
                    }
                </ul>
            </CardContent>
        </Card>
    );
}