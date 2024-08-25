type serverSchema = {
    BACKEND_BASE_URL: string
};

const serverEnv = {
    BACKEND_BASE_URL: String(process.env.NEXT_PUBLIC_BACKEND_BASE_URL)
} satisfies serverSchema;

export {
    serverEnv
};