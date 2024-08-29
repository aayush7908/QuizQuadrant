"use client"

import { CardTitle } from "@/components/ui/card";
import Link from "next/link";
import { Exam } from "@/lib/type/model/Exam";
import { ExternalLink, Link2 } from "lucide-react";

export function DraftExamCard({ data, index }: { data: Exam, index: number }) {
    return (
        <Link href={`/exam/draft/${data.id}`} className="px-3 py-2 border-2 rounded-md">
            <CardTitle className="flex gap-3 justify-between">
                <span className="text-lg">{data.title ? data.title.slice(0, 20) + "..." : "Untitled Draft Exam"}</span>
                <ExternalLink />
            </CardTitle>
            <p className="text-gray-600">Last Modified On: {(new Date(data.lastModifiedOn)).toLocaleString()}</p>
        </Link>
    );
}