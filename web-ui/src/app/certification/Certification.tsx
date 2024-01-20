import {Certificate} from "@/app/lib/definitions";

interface CertificationProps {
  certificate: Certificate;
}

export default function Certification(props: CertificationProps) {

  return (
    <div className="mb-32 grid text-center lg:max-w-10xl lg:w-full lg:mb-0 lg:grid-cols-1 lg:text-center">
      <div data-testid="certificate"
           className="text-black bg-white border-4 mb-4 border-black border-dashed w-3/4 place-self-center m-20">
        <div className="text-xl">
          <p className="p-5">This is to certify that</p>
          <p className="text-2xl italic" data-testid="certified-name">{props.certificate.name}</p>
          <p className="p-5">Was admitted to the certification</p>
        </div>
        <div className="text-5xl text-amber-500 p-5">Scrumfall Master</div>
        <div className="text-9xl p-10">{props.certificate.award}</div>
        <p className="p-5" data-testid="date-certified">{props.certificate.date}</p>
        <div className="text-xs text-green-700 p-10">
          Certificate ID: <span data-testid="certificate-id">{props.certificate.id}</span>
        </div>
      </div>
      <div className="text-xs text-green-700 p-20">
            <span onClick={window.print}
                  className="cursor-pointer underline text-blue-500 font-bold text-xl">Print</span>
      </div>
    </div>
  )
}
