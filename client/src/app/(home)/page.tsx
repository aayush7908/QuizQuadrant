"use client"

import { SubtopicCard } from "@/components/custom/home/SubtopicCard";
import { SubjectContext } from "@/context/subject/SubjectContext";
import { useContext, useEffect } from "react";

export default function Home() {

	const { subjects } = useContext(SubjectContext);

	return (
		<div className="py-[2rem] grid gap-[1rem] md:grid-cols-2 px-[1rem] md:px-[3rem] lg:px-[12rem]">
			{
				subjects && (
					Array.from(subjects.values()).map((subject, index) => {
						return (
							<SubtopicCard key={index} subject={subject} />
						)
					})
				)
			}
		</div>
	);
}