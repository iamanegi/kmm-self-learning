//
//  LoginScreen.swift
//  iosApp
//
//  Created by Aman Negi on 24/09/22.
//  Copyright © 2022 iamanegi. All rights reserved.
//

import SwiftUI
import shared

struct LoginScreen: View {
    
    @State private var showingAlert = false
    @State private var alertTitle = ""
    
    @State var email: String = ""
    @State var password: String = ""
    @State var showPassword: Bool = true
    
    @State var isAuthenticated: Bool? = nil
    
    var body: some View {
        
        VStack(spacing: 8) {
            
            // email
            TextField("Email", text: $email)
                .padding(.horizontal)
                .frame(height: 48)
                .background(.gray.opacity(0.3))
                .cornerRadius(8)
                .disableAutocorrection(true)
                .autocapitalization(.none)
                .textContentType(.emailAddress)
                .keyboardType(.emailAddress)
            
            // password
            ZStack(alignment: .trailing) {
                Group {
                    if (showPassword) {
                        SecureField("Password", text: $password)
                    } else {
                        TextField("Password", text: $password)
                    }
                }.padding(.horizontal)
                    .frame(height: 48)
                    .background(.gray.opacity(0.3))
                    .cornerRadius(8)
                    .disableAutocorrection(true)
                
                Button {
                    showPassword.toggle()
                } label: {
                    Image(systemName: self.showPassword ? "eye.slash" : "eye")
                        .accentColor(.gray)
                }.padding(.horizontal)

            }
            
            // login button
            NavigationLink(
                destination: ProductsListScreen(email: email),
                tag: true,
                selection: $isAuthenticated
            ) {
                Button {
                    login()
                } label: {

                    Text("Login")
                        .font(.headline)
                        .foregroundColor(.white)
                        .frame(height: 48)
                        .padding(.horizontal)

                }.background(Color("accentColor"))
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .shadow(color: .gray.opacity(0.7), radius: 5, y: 5)
                    .padding(.vertical)
            }
            
        }.padding()
            .navigationTitle("Login")
            .alert(isPresented: $showingAlert) {
                Alert(title: Text(alertTitle))
            }
        
    }
    
    private func login() {
        let auth = Auth()
        let authResponse = auth.authenticateUser(email: self.email, password: self.password)
        
        if (authResponse.isAuthenticated) {
            isAuthenticated = true
        } else {
            alertTitle = authResponse.message
            showingAlert = true
        }
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}
