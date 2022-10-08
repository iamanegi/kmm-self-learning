//
//  RatingBar.swift
//  iosApp
//
//  Created by Aman Negi on 08/10/22.
//  Copyright © 2022 iamanegi. All rights reserved.
//

import SwiftUI

struct RatingBar: View {
    var numberOfStars: Int
    var rating: Decimal
    var starHighlightColor: Color
    var starBackgroundColor: Color
    var starSpacing: CGFloat
    
    init(
        numberOfStars: Int = 5,
        rating: Decimal = 0,
        starHighlightColor: Color = .yellow,
        starBackgroundColor: Color = .gray,
        starSpacing: CGFloat = 4
    ) {
        self.numberOfStars = numberOfStars
        self.rating = rating
        self.starHighlightColor = starHighlightColor
        self.starBackgroundColor = starBackgroundColor
        self.starSpacing = starSpacing
    }

    
    var body: some View {
        ZStack {
            BackgroundStars(
                color: starBackgroundColor,
                numberOfStars: numberOfStars,
                starSpacing: starSpacing
            )
            ForegroundStars(
                rating: rating,
                color: starHighlightColor,
                numberOfStars: numberOfStars,
                starSpacing: starSpacing
            )
        }.frame(height: .zero) // to prevent stars from taking full height
    }
    
}


private struct BackgroundStars: View {
    let color: Color
    let numberOfStars: Int
    let starSpacing: CGFloat

    var body: some View {
        HStack(spacing: starSpacing) {
            ForEach(1...numberOfStars, id: \.self) { _ in
                Image(systemName: "star.fill")
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            }
        }.foregroundColor(color)
    }
}


private struct ForegroundStars: View {
    var rating: Decimal
    var color: Color
    let numberOfStars: Int
    let starSpacing: CGFloat

    var body: some View {
        HStack(spacing: starSpacing) {
            ForEach(0..<numberOfStars, id: \.self) { index in
                RatingStar(
                    rating: self.rating,
                    color: self.color,
                    index: index
                )
            }
        }
    }
}


struct RatingStar: View {
    var rating: CGFloat
    var color: Color
    var index: Int
    
    
    var maskRatio: CGFloat {
        let mask = rating - CGFloat(index)
        
        switch mask {
        case 1...: return 1
        case ..<0: return 0
        default: return mask
        }
    }


    init(rating: Decimal, color: Color, index: Int) {
        // Why decimal? Decoding floats and doubles is not accurate.
        self.rating = CGFloat(Double(rating.description) ?? 0)
        self.color = color
        self.index = index
    }


    var body: some View {
        GeometryReader { star in
            Image(systemName: "star.fill")
                .resizable()
                .aspectRatio(contentMode: .fill)
                .foregroundColor(self.color)
                .mask(
                    Rectangle()
                        .size(
                            width: star.size.width * self.maskRatio,
                            height: star.size.height
                        )
                    
                )
        }
    }
}

struct RatingBar_Previews: PreviewProvider {
    static var previews: some View {
        RatingBar(
                numberOfStars: 6,
                rating: 3.7,
                starHighlightColor: .yellow,
                starBackgroundColor: .gray,
                starSpacing: 8
            )
    }
}
