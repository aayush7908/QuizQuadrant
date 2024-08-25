import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import Link from "next/link";

export function QuestionCard() {
    return (
        <Card>
            <CardHeader className="border-b p-[1rem] bg-muted">
                <CardTitle className="flex justify-between items-center">
                    <span className="text-lg">Question - 1</span>
                    <Badge className="text-sm">MCQ</Badge>
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="mt-[1rem] grid gap-3">
                    <div className="flex flex-wrap gap-3">
                        <Badge variant="outline" className="text-base">Subject</Badge>
                        <Badge variant="outline" className="text-base">Subtopic</Badge>
                    </div>
                    <span>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Cum quisquam reiciendis earum voluptatum itaque quibusdam quod sequi eveniet explicabo, neque, dicta eligendi voluptates repellendus animi praesentium dolorem dolore pariatur beatae iure, perspiciatis veniam voluptas suscipit magnam hic! Consequatur cumque assumenda illo optio earum delectus, enim ut esse? Temporibus, ullam nemo.</span>
                    <div className="w-full grid gap-2">
                        <div className="py-2 px-4 rounded-md border">adf</div>
                        <div className="py-2 px-4 rounded-md border">adf</div>
                        <div className="py-2 px-4 rounded-md border">adf</div>
                        <div className="py-2 px-4 rounded-md border">adf</div>
                    </div>
                    <div className="grid gap-3 p-3 border rounded-md bg-green-400">
                        <h3 className="text-center font-semibold">SOLUTION</h3>
                        <span>Lorem ipsum dolor sit amet consectetur adipisicing elit. Tempora ipsum, provident ad ab commodi sunt praesentium laboriosam quae voluptate aperiam expedita illo inventore distinctio vitae. Accusantium officiis voluptatibus ab! Architecto perferendis unde nam minima repellat. Incidunt officiis possimus quaerat impedit explicabo repellat voluptatum, eveniet iure, quasi officia ex provident dicta.</span>
                        <div className="grid">
                            <span className="font-semibold">Correct Option(s):</span>
                            <span>option - a</span>
                            <span>option - a</span>
                            <span>option - a</span>
                        </div>
                    </div>
                </div>
            </CardContent>
            <CardFooter>
                <div className="w-full flex justify-center">
                    <Link href={"/question/edit/abcd-abcd-abcd"} className="bg-black text-white py-2 px-3 rounded-md cursor-pointer">Edit</Link>
                </div>
            </CardFooter>
        </Card>
    );
}