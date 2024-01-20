"use client"

import {useState} from "react";
import HeroImage from "@/app/HeroImage"
import Certification from "@/app/certification/Certification";
import {Certificate} from "@/app/lib/definitions";
import CertificationForm from "@/app/certification/CertificationForm";

export default function Page() {
  const [certificate, setCertificate] =
    useState<Certificate>(new Certificate('', '', ''))

  return (
    <main className="flex min-h-screen flex-col items-center justify-between">
      {(!certificate || certificate.id === '') && (
        <div className="mb-32 grid text-center lg:max-w-10xl lg:w-full lg:mb-0 lg:grid-cols-1 lg:text-center">
          <HeroImage/>
          <CertificationForm setCertificate={setCertificate}/>
        </div>
      )}
      {(certificate.id && certificate.id.length > 0) && (
        <Certification certificate={certificate}/>
      )}
    </main>
  )
}
