
export function getToken() {
    const userData = JSON.parse(sessionStorage.getItem('user') || '{}');
    return userData.token || null;
}


export function setToken(newToken) {
    const userData = JSON.parse(sessionStorage.getItem('user') || '{}');
    userData.token = newToken;
    sessionStorage.setItem('user', JSON.stringify(userData));
}

export function getUserId() {
    const userData = JSON.parse(sessionStorage.getItem('user') || '{}');
    return userData.uid || null;
}