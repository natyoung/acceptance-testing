import Link from "next/link";
import {lusitana} from "@/app/ui/fonts";
import HeroImage from "@/app/HeroImage"

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between">
      <HeroImage/>
      <div className={`${lusitana.className} antialiased mb-32 grid text-center lg:max-w-10xl lg:w-full lg:mb-0 lg:grid-cols-1 lg:text-center`}>
        <h1 className="text-xl">Scrumfall Master Certification</h1>
        <div className="opacity-50">
          We are uncovering better ways of selling certificates. Through this work we have come to value:
          <ul className="p-2">
            <li>Output over Outcomes</li>
            <li>Accountability over Responsibility</li>
            <li>Burning Out, Up, and Down</li>
            <li>Process over People</li>
            <li>Surveillance over Confidence</li>
          </ul>
          <i className="text-amber-500">
            That is, while there is value in the items on the right, we value the items on the left.
          </i>
        </div>
      </div>
      <div className="mb-34 grid text-center lg:max-w-5xl lg:w-full lg:mb-0 lg:grid-cols-2 lg:text-left">
        <Link
          href="/wallet/"
          className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
          data-testid="wallet"
        >
          <h2 className={`mb-3 text-2xl font-semibold`}>
            Wallet{' '}
            <span
              className="inline-block transition-transform group-hover:translate-x-1 motion-reduce:transform-none">
              -&gt;
            </span>
          </h2>
          <p className={`m-0 max-w-[30ch] text-sm opacity-50`}>
            View and add Scrumfall points.
          </p>
        </Link>
        <a
          href="/certification"
          className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
          data-testid="certification"
        >
          <h2 className={`mb-3 text-2xl font-semibold`}>
            Certification{' '}
            <span
              className="inline-block transition-transform group-hover:translate-x-1 motion-reduce:transform-none">
              -&gt;
            </span>
          </h2>
          <p className={`m-0 max-w-[30ch] text-sm opacity-50`}>
            Get certified
          </p>
        </a>
      </div>
    </main>
  )
}
