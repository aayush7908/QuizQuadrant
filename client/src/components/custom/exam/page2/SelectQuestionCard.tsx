"use client"

import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Question } from "@/lib/type/model/Question";
import { Button } from "@/components/ui/button";
import { Option } from "@/lib/type/model/Option";

export function SelectQuestionCard({ data, index, defaultProps }: { data: Question, index: number, defaultProps: Function }) {
    return (
        <Card>
            <CardHeader className="border-b p-[1rem] bg-muted">
                <CardTitle className="flex justify-between items-center">
                    <span className="text-lg">Question - {index + 1}</span>
                    <Badge className="text-sm">{data.type}</Badge>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="mt-[1rem] grid gap-3">
                    <div className="flex flex-wrap gap-3">
                        <Badge variant="outline" className="text-base">{data.subtopic.subject.name}</Badge>
                        <Badge variant="outline" className="text-base">{data.subtopic.name}</Badge>
                        <Badge variant="secondary" className="text-base border border-gray-400">{data.isPublic ? "PUBLIC" : "PRIVATE"}</Badge>
                    </div>
                    <span>{data.statement}</span>
                    <div className="w-full grid gap-2">
                        {
                            data.options.map((option: Option, i: number) => {
                                return (
                                    <div key={i} className={`py-2 px-4 rounded-md border ${option.isCorrect && "bg-green-400"}`}>{option.statement}</div>
                                )
                            })
                        }
                    </div>
                    <div className="grid gap-3 p-3 border rounded-md bg-muted">
                        <h3 className="text-center font-semibold">SOLUTION</h3>
                        <span>{data.solution.statement}</span>
                    </div>
                </div>
            </CardContent>
            <CardFooter className="flex flex-col gap-3">
                <div className="w-full flex gap-3 justify-center">
                    <Button onClick={() => { defaultProps(data); }}>Select</Button>
                </div>
            </CardFooter>
        </Card>
    );
}