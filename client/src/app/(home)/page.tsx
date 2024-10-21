"use client"

import { useContext } from "react";
import { SubjectContext } from "@/context/subject/SubjectContext";
import { SubtopicCard } from "@/app/(home)/_components/SubtopicCard";

export default function Home() {

	const { subjects } = useContext(SubjectContext);

	return (
		<>
			{
				subjects ? (
					<div className="py-[2rem] grid gap-[1rem] md:grid-cols-2 px-[1rem] md:px-[3rem] lg:px-[12rem]">
						{
							Array.from(subjects.values()).map((subject, index) => {
								return (
									<SubtopicCard key={index} subject={subject} />
								)
							})
						}
					</div>
				) : (
					<div className="py-[2rem] flex justify-center">
						<p className="text-xl font-medium">No Data Found</p>
					</div>
				)
			}
		</>
	);
}
