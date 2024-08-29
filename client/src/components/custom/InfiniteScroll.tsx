"use client"

import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";

export function InfiniteScroll({
    fetchFunction,
    totalLength,
    initialData,
    Component,
    componentDefaultProps
}: {
    fetchFunction(pageNumber: number): Promise<any[] | undefined>,
    totalLength: number,
    initialData: Array<any>,
    Component: React.FC<any>,
    componentDefaultProps?: any
}) {

    const [data, setData] = useState<Array<any>>(initialData);
    const [pageNumber, setPageNumber] = useState<number>(1);
    const { ref, inView } = useInView();

    useEffect(() => {
        if (inView && data.length < totalLength) {
            (async () => {
                const fetchedData = await fetchFunction(pageNumber);
                let newData = [...data];
                newData = newData.concat(fetchedData || []);
                setData(newData);
                setPageNumber(pageNumber => pageNumber + 1);
            })();
        }
    }, [inView, totalLength]);

    return (
        <>
            {
                (data && data.length > 0) ? (
                    <>
                        {
                            data.map((componentData, index) => {
                                return (
                                    <Component key={index} data={componentData} defaultProps={componentDefaultProps} index={index} />
                                )
                            })
                        }
                    </>
                ) : (
                    <div className="h-full flex justify-center text-xl">No data to display</div>
                )
            }
            <div className={`w-full flex gap-3 justify-center items-center ${data.length === totalLength && "hidden"}`} ref={ref}>
                <Loader2 className="animate-spin size-5" />
                <span className="font-medium text-lg">Loading More ...</span>
            </div>
        </>
    );
}