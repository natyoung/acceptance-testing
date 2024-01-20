import Image from "next/image";
import styles from "@/app/ui/header.module.css";

const {heroWrapper, imageWrapper} = styles;

export default function Page(props: any) {
  const image: string = `/images/${Math.floor(Math.random() * 4) + 1}.png`
  return (
    <div className={heroWrapper}>
      <div className={imageWrapper}>
        <Image
          priority
          src={image}
          fill
          style={{objectFit: "cover", objectPosition: "center"}}
          alt="Masters of Scrumfall"
        />
      </div>
    </div>
  )
}
