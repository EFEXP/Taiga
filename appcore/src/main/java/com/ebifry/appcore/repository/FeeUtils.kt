package com.ebifry.appcore.repository

import com.ebifry.appcore.domain.entity.PackageDimensions
import com.ebifry.appcore.domain.entity.Product
import com.ebifry.appcore.domain.entity.db.SizeCategory
import java.lang.IllegalStateException
import java.util.*

fun calculateShippingCharge(product: Product): SizeCategory {
    val weightGram:Double = product.packageDimensions.weight*453.592
    val heightCm=product.packageDimensions.height/ 0.3937
    val lengthCm=product.packageDimensions.length/ 0.3937
    val widthCm=product.packageDimensions.width/ 0.3937
    val size=heightCm+lengthCm+widthCm
    val sizeList=listOf(heightCm,lengthCm,widthCm).sortedDescending()
    val spacerSmallAndNormal1Weight=50
    val spacerNormal2toLarge3Weight=150
    val spacerLarge4Weight=550
    val spacerLarge5Weight=700
    val spacerLarge6to7Weight=1200
    val spacerLarge8toExLarge1Weight=1400
    val spacerExLarge2Weight=1500
    val spacerExLarge3Weight=1700
    val spacerExLarge4Weight=1800

    if((sizeList[0]<25) and (sizeList[1]<18) and (sizeList[2]<2)){
        if ((weightGram+spacerSmallAndNormal1Weight)<250)
            return SizeCategory.SMALL
    }
    else if((sizeList[0]<45) and (sizeList[1]<35) and (sizeList[2]<20) and (weightGram<9000)){
        if((sizeList[0]<33) and (sizeList[1]<24) and (sizeList[2]<2.8) and ((weightGram+spacerSmallAndNormal1Weight)<1000))
            return SizeCategory.MEDIUM_1
        if((size<60) and ((weightGram+spacerNormal2toLarge3Weight)<2000))
            return SizeCategory.MEDIUM_2
        if((size<80) and ((weightGram+spacerNormal2toLarge3Weight)<5000))
            return SizeCategory.MEDIUM_3
        if((size<100) and ((weightGram+spacerNormal2toLarge3Weight)<9000))
            return SizeCategory.MEDIUM_4
    }
    else if((sizeList[0]>45) or (sizeList[1]>35) or (sizeList[2]>20) or (weightGram>9000)){
        if((size<60) and ((weightGram+spacerNormal2toLarge3Weight)<2000))
            return  SizeCategory.LARGE_1
        if((size<80) and ((weightGram+spacerNormal2toLarge3Weight)<5000))
            return  SizeCategory.LARGE_2
        if((size<100) and ((weightGram+spacerNormal2toLarge3Weight)<10000))
            return  SizeCategory.LARGE_3
        if((size<120) and ((weightGram+spacerLarge4Weight)<15000))
            return   SizeCategory.LARGE_4
        if((size<140) and ((weightGram+spacerLarge5Weight)<20000))
            return   SizeCategory.LARGE_5
        if((size<160) and ((weightGram+spacerLarge6to7Weight)<25000))
            return  SizeCategory.LARGE_6
        if((size<180) and ((weightGram+spacerLarge6to7Weight)<30000))
            return   SizeCategory.LARGE_7
        if((size<200) and ((weightGram+spacerLarge8toExLarge1Weight)<40000))
            return  SizeCategory.LARGE_8
        if((size<200) and ((weightGram+spacerLarge8toExLarge1Weight)<50000))
            return  SizeCategory.EXTRA_LARGE_1
        if((size<220) and ((weightGram+spacerExLarge2Weight)<50000))
            return  SizeCategory.EXTRA_LARGE_2
        if((size<240) and ((weightGram+spacerExLarge3Weight)<50000))
            return  SizeCategory.EXTRA_LARGE_3
        if((size<260) and ((weightGram+spacerExLarge4Weight)<50000))
            return  SizeCategory.EXTRA_LARGE_4
    }else{
        throw IllegalStateException("Not matched.")
    }
    throw IllegalStateException("Not matched.")
}

fun calculateStorageCharge(date: Date,pkd:PackageDimensions):Double{
    val rate=if (((date.month+1)>9)){ 5.16 }else{ 9.17 }
    return (rate*(pkd.height*pkd.length*pkd.weight/1000))
}
