<template>
<svg class="svg-icon" aria-hidden="true">
    <use :xlink:href="iconName"></use>
  </svg>
</template>

<script>
import './iconfont'
export default {
	name: 'icon',
	props: {
		iconClass: {
			type: String,
			required: true
		}
	},
	computed: {
		iconName() {
			return `#icon-${this.iconClass}`
		}
	}
}
</script>
