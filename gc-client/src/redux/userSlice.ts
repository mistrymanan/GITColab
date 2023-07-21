import { createSlice } from "@reduxjs/toolkit";

export const userSlice = createSlice({
    name: "user",
    initialState: {
        user: null
    },
    reducers: {
        login: (state, action) => {
            state.user = action.payload;
        },
        logout: (state) => {
            state.user = null
        },
        /*This reducer handles updating the state of user profile information after an update action*/
        updateProfile: (state, action) => {
            state.user = action.payload;
        }
    }
});

export const { login, logout, updateProfile } = userSlice.actions;

export const selectUser = (state: any) => state.user;

export default userSlice.reducer;