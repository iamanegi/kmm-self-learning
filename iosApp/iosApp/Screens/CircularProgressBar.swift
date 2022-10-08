//
//  CircularProgressBar.swift
//  iosApp
//
//  Created by Aman Negi on 08/10/22.
//  Copyright © 2022 iamanegi. All rights reserved.
//

import SwiftUI

struct CircularProgressBar: View {
    let width: CGFloat
    let color: Color
    
    @State private var revolving = true
    
    var body: some View {
        ZStack { // 1
            Circle()
                .stroke(
                    color.opacity(0.5),
                    lineWidth: width
                )
            Circle() // 2
                .trim(from: 0, to: 0.65)
                .stroke(
                    color,
                    style: StrokeStyle(
                        lineWidth: width,
                        lineCap: .round
                    )
                )
                .rotationEffect(.degrees(revolving ? -360 : 360))
                .animation(
                    .linear(duration: 3).repeatForever(autoreverses: false),
                    value: revolving)
        }.aspectRatio(1, contentMode: .fit)
            .frame(width: 100)
            .task {
                revolving.toggle()
            }
    }
}

struct CircularProgressBar_Previews: PreviewProvider {
    static var previews: some View {
        CircularProgressBar(width: 8, color: .pink)
    }
}
