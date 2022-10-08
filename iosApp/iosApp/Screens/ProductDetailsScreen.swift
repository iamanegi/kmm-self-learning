//
//  ProductDetailsScreen.swift
//  iosApp
//
//  Created by Aman Negi on 08/10/22.
//  Copyright © 2022 iamanegi. All rights reserved.
//

import SwiftUI
import shared

struct ProductDetailsScreen: View {
    let product: Product
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(alignment: .leading, spacing: 8) {
                
                HorizontalPager(pageCount: (product.images?.count) ?? 0) { index in
                    AsyncImage(
                        url: URL(string: (product.images?[index] ?? ""))) { image in
                            image.resizable()
                                .aspectRatio(1, contentMode: .fit)
                        } placeholder: {
                            Image("placeholder")
                                .resizable()
                                .aspectRatio(1, contentMode: .fit)
                        }
                }
                
                Text(product.description_ ?? "")
                    .font(.system(.body))
                    .padding(.horizontal)
                
                HStack (alignment: .center, spacing: 16) {
                    if let discount = product.discountPercentage {
                        Text("\(discount)% off")
                            .font(.system(.body))
                            .fontWeight(.bold)
                            .foregroundColor(.green)
                    }
                    
                    Text("₹\(product.price ?? 0)")
                        .font(.system(.body))
                        .fontWeight(.bold)
                }.padding(.horizontal)
                
                HStack (alignment: .bottom, spacing: 8) {
            
                    RatingBar(
                        rating: Decimal(floatLiteral: Double(truncating: product.rating!))
                    ).frame(width: UIScreen.main.bounds.width * 0.3)
                        .padding(.vertical)
                    
                    Text("\(product.rating ?? 0)")
                        .font(.system(.caption))
                        .padding(.vertical, 4)

                }.padding(.horizontal)
                
                Text("Only \(product.stock!) left!")
                    .font(.system(.subheadline))
                    .foregroundColor(.white)
                    .padding(.vertical, 8)
                    .frame(maxWidth: .infinity)
                    .background(.gray)
                    .cornerRadius(8)
                    .padding(.horizontal)
                    
            }
        }.navigationTitle(product.title ?? "Product Detail")

    }
}

struct HorizontalPager<Content: View>: View {
    
    let pageCount: Int
    let content: (Int) -> Content
    
    var body: some View {
        LazyHStack {
            TabView {
                ForEach(0..<pageCount, id: \.self) { i in
                    content(i)
                }
            }.aspectRatio(1, contentMode: .fit)
                .frame(width: UIScreen.main.bounds.width)
                .tabViewStyle(PageTabViewStyle())
            
        }
    }
}

struct ProductDetailsScreen_Previews: PreviewProvider {
    static var previews: some View {
        ProductDetailsScreen(product: dummyProduct)
    }
}
