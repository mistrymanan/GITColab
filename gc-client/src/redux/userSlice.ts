import { createSlice } from "@reduxjs/toolkit";

export const userSlice = createSlice({
    name: "user",
    initialState: {
        user: null,
        userData: {},
    },
    reducers: {
        login: (state, action) => {
            state.user = action.payload;
        },
        logout: (state) => {
            state.user = null;
        },
        user: (state, action) => {
            state.user = action.payload;
        }
    }
});

export const { login, logout } = userSlice.actions;

export const selectUser = (state: any) => state.user;

// export const getUserData = (state: any) => state.user;

export default userSlice.reducer;