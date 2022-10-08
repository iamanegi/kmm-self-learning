//
//  ProductsListScreen.swift
//  iosApp
//
//  Created by Aman Negi on 07/10/22.
//  Copyright © 2022 iamanegi. All rights reserved.
//

import SwiftUI
import shared

let dummyProduct = Product(
    id: 1,
    title: "iPhone 9",
    description: "An apple mobile which is nothing like apple",
    price: 549,
    discountPercentage: 12.96,
    rating: 4.69,
    stock: 94,
    brand: "Apple",
    category: "smartphones",
    thumbnail: "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
    images: [
        "https://dummyjson.com/image/i/products/1/1.jpg",
        "https://dummyjson.com/image/i/products/1/2.jpg",
        "https://dummyjson.com/image/i/products/1/3.jpg",
        "https://dummyjson.com/image/i/products/1/4.jpg",
        "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
    ]
)

let dummyProduct2 = Product(
    id: 2,
    title: "iPhone 9",
    description: "An apple mobile which is nothing like apple",
    price: 549,
    discountPercentage: 12.96,
    rating: 4.69,
    stock: 94,
    brand: "Apple",
    category: "smartphones",
    thumbnail: "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
    images: [
        "https://dummyjson.com/image/i/products/1/1.jpg",
        "https://dummyjson.com/image/i/products/1/2.jpg",
        "https://dummyjson.com/image/i/products/1/3.jpg",
        "https://dummyjson.com/image/i/products/1/4.jpg",
        "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
    ]
)

let dummyProduct3 = Product(
    id: 3,
    title: "iPhone 9",
    description: "An apple mobile which is nothing like apple",
    price: 549,
    discountPercentage: 12.96,
    rating: 4.69,
    stock: 94,
    brand: "Apple",
    category: "smartphones",
    thumbnail: "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
    images: [
        "https://dummyjson.com/image/i/products/1/1.jpg",
        "https://dummyjson.com/image/i/products/1/2.jpg",
        "https://dummyjson.com/image/i/products/1/3.jpg",
        "https://dummyjson.com/image/i/products/1/4.jpg",
        "https://dummyjson.com/image/i/products/1/thumbnail.jpg"
    ]
)

let dummyProductsResponse = ProductsResponse(
    products: [dummyProduct, dummyProduct2, dummyProduct3], total: 100, skip: 0, limit: 2
)

struct ProductsListScreen: View {
    
    let email: String?
    
    @State private var showingAlert = false
    @State private var alertTitle = ""
    
    @State var productsResponse: ProductsResponse? = nil
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            
            VStack(spacing: 16) {
                
                Text("Welcome, \(email ?? "there")")
                    .font(.system(.title3))
                    .padding(.vertical, 8)
                    .frame(maxWidth: .infinity)
                    .background(Color("primaryColor"))
                
                if (productsResponse == nil) {
                    Spacer(minLength: 100)
                    CircularProgressBar(width: 6, color: Color("accentColor"))
                }
                
                if let productsResponse = productsResponse {
                    LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 36) {
                        ForEach(productsResponse.products ?? [], id: \.id) { product in
                            ProductItemView(product: product)
                        }
                    }.padding(.horizontal, 16)
                }
            }
            
        }.navigationTitle("Products")
            .navigationBarBackButtonHidden()
            .onAppear() {
                fetchProducts()
            }
            .alert(isPresented: $showingAlert) {
                Alert(title: Text(alertTitle))
            }
                
    }
    
    struct ProductItemView: View {
        let product: Product

        var body: some View {
            NavigationLink(destination: ProductDetailsScreen(product: product)) {
                VStack(alignment: .leading, spacing: 4) {
                    AsyncImage(url: URL(string: product.thumbnail ?? "")) { image in
                        image.resizable()
                            .aspectRatio(1, contentMode: .fit)
                    } placeholder: {
                        Image("placeholder")
                            .resizable()
                            .aspectRatio(1, contentMode: .fit)
                    }
                    Text(product.title ?? "")
                        .font(.system(.subheadline))
                    HStack (alignment: .center, spacing: 8) {
                        Text("₹\(product.price ?? 0)")
                            .font(.system(.body))
                            .fontWeight(.bold)
                        
                        if let discount = product.discountPercentage {
                            Text("\(discount)% off")
                                .font(.system(.body))
                                .fontWeight(.bold)
                                .foregroundColor(.green)
                        }
                    }
                    GeometryReader { metrics in
                        RatingBar(
                            rating: Decimal(floatLiteral: Double(truncating: product.rating!))
                        ).frame(width: metrics.size.width * 0.7)
                    }.frame(width: .infinity, height: .zero) // to prevent GeometryReader from taking extra space
                        .padding(.vertical, 8)
                    Spacer()
                }
            }
        }
    }

    func fetchProducts() {
        KtorHelper().getProducts { response, _ in
            if let response = response {
                self.productsResponse = response
            } else {
                alertTitle = "Something went wrong"
                showingAlert = true
            }
        }
    }
}

struct ProductsListScreen_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListScreen(email: "none@test.com", productsResponse: dummyProductsResponse)
    }
}
