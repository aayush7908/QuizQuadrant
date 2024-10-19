import Cookies from "js-cookie";

const TOKEN_NAME = "authToken";
const PASSWORD_RESET_TOKEN_NAME = "passwordResetToken";

export const getToken = () => {
    return Cookies.get(TOKEN_NAME);
}

export const setToken = (token: string) => {
    Cookies.set(TOKEN_NAME, token, { expires: 7 });
}

export const removeToken = () => {
    Cookies.remove(TOKEN_NAME);
}

export const getPasswordResetToken = () => {
    return Cookies.get(PASSWORD_RESET_TOKEN_NAME);
}

export const setPasswordResetToken = (token: string) => {
    Cookies.set(PASSWORD_RESET_TOKEN_NAME, token);
}

export const removePasswordResetToken = () => {
    Cookies.remove(PASSWORD_RESET_TOKEN_NAME);
}