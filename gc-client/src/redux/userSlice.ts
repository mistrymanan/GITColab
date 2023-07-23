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
            state.user = null
            state.userData = {}
        },
        /*This reducer handles updating the state of user profile information after an update action*/
        updateProfile: (state, action) => {
            state.userData = action.payload;
        }
    }
});

export const { login, logout, updateProfile } = userSlice.actions;

export const selectUser = (state: any) => state.user;

export const selectUserData = (state:any) => state.userData;

export default userSlice.reducer;