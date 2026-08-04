import { ZipWriter, TextReader } from "@zip.js/zip.js";
import { BlobWriter } from "@zip.js/zip.js";

let id = 0;

const addEntry = () => {
  let div = document.createElement("div")
  div.id = `${id}-div`
  div.classList.add('generator-box')

  let input = document.createElement("input")
  input.placeholder = "Enter block id (e.g: minecraft:cobblestone)"
  div.appendChild(input)

  let deletebutton = document.createElement("button")
  let trashicon = document.createElement("img")
  trashicon.src = '/trash.svg';
  deletebutton.appendChild(trashicon)
  deletebutton.onclick = () => { document.getElementById(`${div.id}`)!.remove() }
  deletebutton.classList.add("deletebutton")
  div.appendChild(deletebutton)

  document.getElementById('generators')!.appendChild(div)
  id += 1;
}
document.getElementById('add')!.onclick = addEntry;
addEntry()

document.getElementById('download')!.onclick = async () => {
  const file = new BlobWriter()
  const mcmeta = new TextReader(JSON.stringify({
    pack: {
      pack_format: 48,
      description: "Custom generators for the Create Cobblestone mod"
    }
  }))
  const writer = new ZipWriter(file)
  await writer.add("pack.mcmeta", mcmeta)

  let valid = true;

  document.getElementById('generators')?.childNodes.forEach(async node => {
    if (!valid) return

    let input = node.firstChild as HTMLInputElement
    if (!RegExp(/^[a-z0-9._-]+:[a-z0-9._/-]+$/i).test(input.value)) {
      valid = false;
      alert(`Invalid block id: ${input.value}`)
      return
    }

    let block = input.value.split(':')[1];

    const generator = new TextReader(JSON.stringify({
      block: input.value,
    }))

    await writer.add(`data/custom/generator_types/${block}.json`, generator)
  })

  if (!valid) {
    return
  }

  await writer.close()

  let link = document.createElement('a')
  link.href = URL.createObjectURL(await file.getData())
  link.download = "datapack.zip"
  link.click()
}
